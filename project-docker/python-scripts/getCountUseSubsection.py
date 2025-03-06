import requests
import sys
import os
import json
import result.PythonResult as PythonResult

curPath = os.path.abspath(os.path.dirname(__file__))
rootPath = os.path.split(curPath)[0]
sys.path.append(rootPath)

User_List = {
    'Name': sys.argv[1],
    'Password': sys.argv[2],
    'SubsectionId': sys.argv[3]
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
    qu = User_List['SubsectionId']
    # 设置临时的问题集
    qustionlst = []
    # 首先尝试请求答题
    questionParam = {
        'kpId': qu
    }
    questionreq = session.get(questionUrl, headers=answer_headers, params=questionParam)
    try:
        # 获取到问题id
        for questionItme in questionreq.json()['data']['questionList']:
            qustionlst.append(questionItme['id'])
            PythonResult.result['questionCount'] += 1

    except:
        PythonResult.getResultFalseLogin()
    questionreq.close()
    PythonResult.result['subsectionTdList'].append(qu)
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

# print(token)#确定登陆成功

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
