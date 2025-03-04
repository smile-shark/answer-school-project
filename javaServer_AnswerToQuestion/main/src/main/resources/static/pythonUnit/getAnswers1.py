
import requests
import pymysql
#尝试异步：
import asyncio
import aiohttp
import sys
#多线程：
from concurrent.futures import ThreadPoolExecutor

#固定量
#账户登陆url(post)
login_url = 'https://ai.cqzuxia.com/connect/token'
#错题集url(get)
answer_url = 'https://ai.cqzuxia.com/evaluation/api/StuCenter/GetFaultQuestionList'

db=pymysql.connect(
    host='localhost',
    password='123456',
    user='root',
    database='newMySQLBase'
)

#创建一个游标对象
cursor=db.cursor()
cursor.close()
db.close()

#插入数据

def addAnswer(question):
    str=''
    allstr=''
    for i in range(4):
        if i<len(question['answer']):
            str1=question['answer'][i]['answer_content'].replace("'", "''")
            str1=str1.replace("\\","\\\\")
            str+=f"""
                '{question['answer'][i]['answer_id']}',
                '{str1}',"""
        else:
            str+="null,null,"
    str=str.strip(',')
    try:
        db=pymysql.connect(
            host='localhost',
            password='123456',
            user='root',
            database='newMySQLBase'
        )

        #创建一个游标对象
        cursor=db.cursor()
        allstr+=f"""insert into questionANDAnswer (question_id, question, answer_id_1, answer1, answer_id_2, answer2, answer_id_3, answer3, answer_id_4, answer4) 
            values(
            '{question['questionID']}',
            '{question['questionTitle'].replace("'", "''")}',
            {str}    
            );"""
        db.ping(reconnect=True)
        cursor.execute(allstr)
        db.commit()
        print('写入成功',end=' ')
        
        cursor.close()
        db.close()
    except Exception as error:
        print('语句：',allstr)
        print(error)
        db.ping(reconnect=True)


#异步访问
async def getAnswer(pageN,answer_headers):
    #请求参数
    answer_params = {
        'PageIndex': pageN,#页码
        'PageSize': 10,
        'DataCount': 0
    }

    async with aiohttp.ClientSession() as session:

    #尝试获取数据
        async with session.get(answer_url,headers=answer_headers,params=answer_params) as resp_answer:
            answer=await resp_answer.json()

            #尝试格式化需要的数据
            for question in answer['data']['data']:
                #查询数据中是否有该问题
                try:
                    db=pymysql.connect(
                        host='localhost',
                        password='123456',
                        user='root',
                        database='newMySQLBase'
                    )

                    #创建一个游标对象
                    cursor=db.cursor()
                    db.ping(reconnect=True)
                    cursor.execute(f"select question_id from questionANDAnswer where question_id='{question['questionID']}';")
                    results=cursor.fetchall()
            
                    if(not results):
                        althrow={}
                        althrow['questionID']=question['questionID']
                        althrow['questionTitle']=question['questionTitle']
                        althrow['answer']=[]
                        for answer in question['answerList']:
                            if(answer['isTrue']):
                                althrow['answer'].append({
                                    'answer_id':answer['answerID'],
                                    'answer_content':answer['answerContent'],
                                })
                            else:
                                althrow['answer'].append({
                                    'answer_id':'该位置为错误答案',
                                    'answer_content':answer['answerContent'],
                                })
                        #插入数据
                        addAnswer(althrow)
                        pass
                    else:
                        print('该问题已存在',end=' ')
                        pass
                    cursor.close()
                    db.close()
                except Exception as error:
                    db.ping(reconnect=True)
                    print(error)



#异步访问
async def main(max_page,answer_headers):
    tasks=[]
    max_pageN=1
    while max_pageN<=max_page:
        tasks.append(asyncio.create_task(getAnswer(max_pageN,answer_headers)))
        max_pageN+=1
    await asyncio.wait(tasks)


#中间函数
def zhogn(tokenUser):
    #模拟账户登陆（同步）
    resp=requests.post(login_url,data=tokenUser)
    token=resp.json()
    resp.close()
        
    #模拟错题集访问
    #最大页数（初始设置为1，当第一次查找后就进行更改）
    #用于判断大于最大页数就中断循环
    #请求头
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
    #先使用同步访问一次获取到最大页数        max_page=1
    resp_page=requests.get(answer_url,headers=answer_headers,params={'PageIndex':1,'PageSize':10,'DataCount':0})
    max_page=resp_page.json()['data']['dataCount']//10+1
    resp_page.close()
    asyncio.run(main(max_page,answer_headers))
    print('运行完毕')



def run():
    #创建线程池
    with ThreadPoolExecutor(len(User_List)) as t:
        for item in User_List:
            #账户验证token（请求内容）
            tokenUser={
                'username': item['Name'],                    #账号
                'password': item['Password'],                #密码
                'code': '2341',
                'vid': '',
                'client_id': '43215cdff2d5407f8af074d2d7e589ee',
                'client_secret': 'DBqEL1YfBmKgT9O491J1YnYoq84lYtB/LwMabAS2JEqa8I+r3z1VrDqymjisqJn3',
                'grant_type': 'password',
                'tenant_id': '32'
            }
            
            #多线程访问
            t.submit(zhogn,tokenUser=tokenUser)     
    print('数据库连接已关闭')


User_List=[]
User_List.append({'Name':sys.argv[1],'Password':sys.argv[2]})
run()