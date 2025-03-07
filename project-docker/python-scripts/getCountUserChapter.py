import requests
import time
import json
import pymysql
import sys
import os
import result.PythonResult as PythonResult

curPath = os.path.abspath(os.path.dirname(__file__))
rootPath = os.path.split(curPath)[0]
sys.path.append(rootPath)

User_List = {
    'Name': sys.argv[1],
    'Password': sys.argv[2],
    'CourseId': sys.argv[3],
    'ChapterId': sys.argv[4]
}

# 登陆位置
logUrl = 'https://ai.cqzuxia.com/connect/token'
# 所有课程
course = 'https://ai.cqzuxia.com/evaluation/api/stuevaluatereport/GetCourseProgram?'
# 小节
Subsection = 'https://ai.cqzuxia.com/evaluation/api/studentevaluate/GetCourseInfoByCourseId'
# 获取到问题
questionUrl = 'https://ai.cqzuxia.com/evaluation/api/studentevaluate/beginevaluate'
# 答题次数限制
limitation = 3

session = requests.Session()


def get_SubID():
    idParam = {
        'CourseID': User_List['CourseId']
    }
    # 需要查询数据库的小节id
    SubsectionID = []

    # 将每个小节的id存入列表
    reqSubsection = session.get(Subsection, params=idParam, headers=answer_headers)
    # 循环每个章节
    try:
        for chapter in reqSubsection.json()['data']['chapters']:
            # 找到这个章节
            if chapter['id'] == User_List['ChapterId']:
                try:
                    for Sub in chapter['knowledgeList']:
                        # 判断已经答题的次数
                        if not Sub['testMemberInfo']:
                            # 如果没有答题过
                            SubsectionID.append(Sub['id'])
                        elif Sub['testMemberInfo']['times'] < limitation and not Sub['testMemberInfo']['isPass']:
                            SubsectionID.append(Sub['id'])
                except:
                    pass
                break
    except:
       pass
    reqSubsection.close()

    for qu in SubsectionID:
        # 设置临时的问题集
        question = []
        # 首先尝试请求答题
        questionParam = {
            'kpId': qu
        }
        questioning = session.get(questionUrl, headers=answer_headers, params=questionParam)
        try:
            # 获取到问题id
            for questioned in questioning.json()['data']['questionList']:
                question.append(questioned['id'])
                PythonResult.result['questionCount'] += 1

        except Exception as e:
            pass
        questioning.close()
    PythonResult.result['subsectionTdList'] = SubsectionID
    print(json.dumps(PythonResult.result))


# 登陆账号
tokenUser = {
    'username': User_List['Name'],  # 账号
    'password': User_List['Password'],  # 密码
    'code': '2341',
    'vid': '',
    'client_id': '43215cdff2d5407f8af074d2d7e589ee',
    'client_secret': 'DBqEL1YfBmKgT9O491J1YnYoq84lYtB/LwMabAS2JEqa8I+r3z1VrDqymjisqJn3',
    'grant_type': 'password',
    'tenant_id': '32'
}

resptaken = session.post(logUrl, data=tokenUser)
token = resptaken.json()
resptaken.close()

try:
    token['token_type']
except:
    PythonResult.getResultFalseLogin()
    sys.exit(1)


# 设置验证的头部
answer_headers = {
    'Accept':
        'application/json, text/plain, */*',
    'Accept-Encoding':
        'gzip, deflate, br, zstd',
    'Accept-Language':
        'zh-CN,zh-TW;q=0.9,zh;q=0.8,en-US;q=0.7,en;q=0.6',
    'Authorization': token['token_type'] + ' ' + token['access_token'],
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
get_SubID()
