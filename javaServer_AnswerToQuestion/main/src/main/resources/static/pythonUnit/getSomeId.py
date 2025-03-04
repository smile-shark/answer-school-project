
import requests
import time
import json
import pymysql
import sys
import os

User_List={
    'Name':sys.argv[1],
    'Password':sys.argv[2]
    }

#登陆位置
logUrl='https://ai.cqzuxia.com/connect/token'
#所有课程
course='https://ai.cqzuxia.com/evaluation/api/stuevaluatereport/GetCourseProgram?'
#小节
Subsection='https://ai.cqzuxia.com/evaluation/api/studentevaluate/GetCourseInfoByCourseId'
#获取到问题
questionUrl='https://ai.cqzuxia.com/evaluation/api/studentevaluate/beginevaluate'
#答题地址：
answerUrl='https://ai.cqzuxia.com/evaluation/api/StudentEvaluate/SaveEvaluateAnswer'
# answerUrl='https://ai.cqzuxia.com/evaluation/api/StudentEvaluate/SaveTestMemberInfo'
#答题次数限制
limitation=3

session=requests.Session()

def answerQuestions(SbusectionID_now,qustionlst):
    #尝试答题：（空回答）
    for answertiems in qustionlst:
        #内容
        Ddata={
            "kpid":SbusectionID_now,
            "questions":[
                {
                    "QuestionID":answertiems,
                    "AnswerID":"error"  
                }]
            }
        #头部
        head2={
            'Accept':
            'application/json, text/plain, */*',
            'Accept-Encoding':
            'gzip, deflate, br, zstd',
            'Accept-Language':
            'zh-CN,zh-TW;q=0.9,zh;q=0.8,en-US;q=0.7,en;q=0.6',
            'Authorization': answer_headers['Authorization'],
            'Content-Length':
            str(len(json.dumps(Ddata))),              #这里有所不同（没有关系）
            'Content-Type':
            'application/json;charset=UTF-8',
            'Origin':
            'https://ai.cqzuxia.com',
            'Priority':
            'u=1, i',
            'Referer':
            'https://ai.cqzuxia.com/',
            'Sec-Ch-Ua':
            '"Not/A)Brand";v="8", "Chromium";v="126", "Google Chrome";v="126"',
            'Sec-Ch-Ua-Mobile':
            '?0',
            'Sec-Ch-Ua-Platform':
            '"Windows"',
            'Sec-Fetch-Dest':
            'empty',
            'Sec-Fetch-Mode':
            'cors',
            'Sec-Fetch-Site':
            'same-origin',
            'User-Agent':
            'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36',
        }
        ansreq=session.post(answerUrl,headers=head2,json=Ddata)
        print('请求:',ansreq.json())
        ansreq.close()
    #最后一次返回
    ansreq=session.post('https://ai.cqzuxia.com/evaluation/api/StudentEvaluate/SaveTestMemberInfo',
                         headers=head2,json={"kpid":SbusectionID_now,"questions":[]})
    print('最后请求:',ansreq.json())
    ansreq.close()

def get_SubID(courseId):
    #尝试通过课程id获取到小节id
    idParam={
        'CourseID':courseId       #自设定id||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    }
    #需要查询数据库的小节id
    SubsectionID=[]
    #从来没有答过的小节id
    SbusectionID_NOT=[]

    #将每个小节的id存入列表
    reqSubsection=session.get(Subsection,params=idParam,headers=answer_headers)
    #循环每个章节
    try:
        for chapter in reqSubsection.json()['data']['chapters']:
            #循环每个小节（有可能会出现有章节没有小节的情况）
            try:
                for Sub in chapter['knowledgeList']:
                    '''在这里将课程章节和小节传入到数据库中
                    1.首先判断数据库中是否有这个小节
                    2.如果有就跳过关于这个小节的一系列操作，如果没有就进行添加
                    3.（在没有小节的情况下）
                    4.判断是否有章节，如果没有就添加
                    4.判断是否有课程，如果没有就添加
                    '''
                    #判断是否有小节
                    db=pymysql.connect(
                        host='localhost',
                        password='123456',
                        user='root',
                        database='newMySQLBase'
                    )
                    cursor=db.cursor()
                    str=f"""select * from Subsection where SubsectionId='{Sub['id']}'"""
                    cursor.execute(str)
                    result=cursor.fetchone()
                    if result:
                        #如果有小节就跳过
                        cursor.close()
                        db.close()
                    else:
                        #如果没有小节就添加
                        str=f"""insert into Subsection values ('{Sub['id']}','{Sub['knowledge']}','{Sub['courseID']}','{Sub['chapterID']}')"""
                        cursor.execute(str)
                        db.commit()

                        #判断是否有章节
                        cursor.execute(f"""select * from chapter where chapterId='{Sub['chapterID']}'""")
                        result=cursor.fetchone()
                        if not result:
                            #如果没有章节就添加
                            str=f"""insert into chapter values ('{Sub['chapterID']}','{Sub['chapterTitle']}','{Sub['titleContent']}','{Sub['courseID']}')"""
                            cursor.execute(str)
                            db.commit()

                        #判断是否有课程
                        cursor.execute(f"""select * from courseTable where courseId='{Sub['courseID']}'""")
                        result=cursor.fetchone()
                        if not result:
                            #如果没有课程就添加
                            str=f"""insert into courseTable values ('{Sub['courseID']}','{Sub['courseName']}')"""
                            cursor.execute(str)
                            db.commit()
                        cursor.close()
                        db.close()





                    #判断已经答题的次数
                    if not Sub['testMemberInfo']:
                        #如果没有答题过
                        SbusectionID_NOT.append(Sub['id'])
                    elif Sub['testMemberInfo']['times']<limitation and not Sub['testMemberInfo']['isPass']:
                        SubsectionID.append(Sub['id'])
            except:
                print('没有小节')
    except Exception as error:
        print('没有章节:',error)
    reqSubsection.close()

    return#这里先暂停一下，先把数据库的操作完成，然后再进行下一步的操作

#登陆账号
tokenUser={
            'username': User_List['Name'],                    #账号
            'password': User_List['Password'],                #密码
            'code': '2341',
            'vid': '',
            'client_id': '43215cdff2d5407f8af074d2d7e589ee',
            'client_secret': 'DBqEL1YfBmKgT9O491J1YnYoq84lYtB/LwMabAS2JEqa8I+r3z1VrDqymjisqJn3',
            'grant_type': 'password',
            'tenant_id': '32'
        }

resptaken=session.post(logUrl,data=tokenUser)
token=resptaken.json()
resptaken.close()

try:
    token['token_type']
except:
    print('登陆失败')
    sys.exit()
    
# print(token)#确定登陆成功

#设置验证的头部
answer_headers = {
            'Accept':
            'application/json, text/plain, */*',
            'Accept-Encoding':
            'gzip, deflate, br, zstd',
            'Accept-Language':
            'zh-CN,zh-TW;q=0.9,zh;q=0.8,en-US;q=0.7,en;q=0.6',
            'Authorization':token['token_type']+' '+token['access_token'],
            'Priority':
            'u=1, i',
            'Referer':
            'https://ai.cqzuxia.com/',
            'Sec-Ch-Ua':
            '"Not/A)Brand";v="8", "Chromium";v="126", "Google Chrome";v="126"',
            'Sec-Ch-Ua-Mobile':
            '?0',
            'Sec-Ch-Ua-Platform':
            '"Windows"',
            'Sec-Fetch-Dest':
            'empty',
            'Sec-Fetch-Mode':
            'cors',
            'Sec-Fetch-Site':
            'same-origin',
            'User-Agent':
            'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36'
    }

#尝试获取课程id
reqAllcourse=session.get(course,headers=answer_headers)
courseId=[]
for reqcourseid in reqAllcourse.json()['data']['unfinished']:
    courseId.append(reqcourseid['courseID'])
#completeLisst是已经完成了的,unfinished是未完成的
for reqcourseid in reqAllcourse.json()['data']['completeList']:
    courseId.append(reqcourseid['courseID'])
# print(courseId)   获取到了课程id存放在courseId的列表中
for courseid in courseId:
    get_SubID(courseid)
reqAllcourse.close()