import datetime
import requests
import time
import json
import pymysql
import sys
import os
import result.PythonResult as PythonResult
import MysqlConfig

curPath = os.path.abspath(os.path.dirname(__file__))
rootPath = os.path.split(curPath)[0]
sys.path.append(rootPath)

# 登陆位置
logUrl = 'https://ai.cqzuxia.com/connect/token'
# 所有课程
course = 'https://ai.cqzuxia.com/evaluation/api/stuevaluatereport/GetCourseProgram?'
# 小节
Subsection = 'https://ai.cqzuxia.com/evaluation/api/studentevaluate/GetCourseInfoByCourseId'
# 获取到问题
questionUrl = 'https://ai.cqzuxia.com/evaluation/api/studentevaluate/beginevaluate'
# 答题地址：
answerUrl = 'https://ai.cqzuxia.com/evaluation/api/StudentEvaluate/SaveEvaluateAnswer'
# 答题次数限制
limitation = 3

session = requests.Session()


# 使用数据库答题
def answerQuestions2(SbusectionID_now, qustionlst,answer_headers):
    for answertiems in qustionlst:
        # 链接到数据中查找答案
        db = pymysql.connect(
            host=MysqlConfig.host,
            password=MysqlConfig.password,
            user=MysqlConfig.user,
            database=MysqlConfig.database
        )

        # 创建一个游标对象
        cursor = db.cursor()
        # 查询语句
        str = f"""select answers from question_and_answer where question_id='{answertiems}';"""
        db.ping(reconnect=True)
        cursor.execute(str)
        result = cursor.fetchone()

        # 获取答案
        strAnswer = ''
        if result:
            result = result[0].split('LBT_1534_LX_5212_WZL_4818')

            for i in range(0, len(result), 2):
                if result[i] and result[i] != '该位置为错误答案':
                    strAnswer += result[i] + ','

            strAnswer = strAnswer[:-1]
            PythonResult.result['finishContent'] += "\n is success to answer question " + answertiems + "\n"
        else:
            # 数据库中没有答案就返回一个error内容
            strAnswer += 'error'
            PythonResult.result['finishContent'] += '\nThe answer is not available in the database ===>\t' + answertiems

        # 关闭数据库
        cursor.close()
        db.close()

        # 内容
        Ddata = {
            "kpid": SbusectionID_now,
            "questions": [
                {
                    "QuestionID": answertiems,
                    "AnswerID": strAnswer
                }]
        }
        # 头部
        head2 = {
            'Accept':
                'application/json, text/plain, */*',
            'Accept-Encoding':
                'gzip, deflate, br, zstd',
            'Accept-Language':
                'zh-CN,zh-TW;q=0.9,zh;q=0.8,en-US;q=0.7,en;q=0.6',
            'Authorization': answer_headers['Authorization'],
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
        ansreq = session.post(answerUrl, headers=head2, json=Ddata)
        PythonResult.result['finishContent'] += "\n answer request \n" + ansreq.text
        ansreq.close()
    # 最后一次返回
    ansreq = session.post('https://ai.cqzuxia.com/evaluation/api/StudentEvaluate/SaveTestMemberInfo',
                          headers=head2, json={"kpid": SbusectionID_now, "questions": []})
    PythonResult.result['finishContent'] += "\n last request " + SbusectionID_now + "\n" + ansreq.text
    ansreq.close()


def get_SubID(userList):
    # 登陆账号
    tokenUser = {
        'username': userList['Name'],  # 账号
        'password': userList['Password'],  # 密码
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
    except Exception as error:
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
    qu = userList['SubsectionId']
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
            PythonResult.result['finishCount'] += 1
            qustionlst.append(questionItme['id'])
        answerQuestions2(qu, qustionlst,answer_headers)
    except Exception as error:
        PythonResult.getResultFalseLogin()
    questionreq.close()


if __name__ == '__main__':
    userList = {
        'Name': sys.argv[1],
        'Password': sys.argv[2],
        'SubsectionId': sys.argv[3]
    }

    get_SubID(userList)
    print(json.dumps(PythonResult.result))
