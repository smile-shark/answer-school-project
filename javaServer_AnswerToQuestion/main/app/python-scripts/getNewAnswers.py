# 在有新课程加入的时候用来获取新的答案
import requests
import json
import time
import sys
import pymysql
import getAnswers1
import MysqlConfig
import result.PythonResult as PythonResult
import getCountUseCourseId as getSubIds
import answerQuestionsBySection as answer

# 从数据库中获取到测试的账号，从外部获取到课程的id，
"""
1. 先从数据库中获取到测试的账号
2. 每个人分别进行答题，和获取答案
3. 重复3次间隔6分钟
"""


def get_test_user():
    try:
        print('数据库连接中...')
        db = pymysql.connect(
            port=MysqlConfig.port,
            host=MysqlConfig.host,
            password=MysqlConfig.password,
            user=MysqlConfig.user,
            database=MysqlConfig.database
        )
        print('连接成功...')
        cursor = db.cursor()
        db.ping(reconnect=True)
        print('开始查询数据...')
        cursor.execute(f"select user_id,user_password from user where data=1")
        print('获取到数据:')
        result = cursor.fetchall()
        cursor.close()
        db.close()
        return result
    except Exception as e:
        print(e)
        PythonResult.getResultFalseLogin()
        return []


if __name__ == '__main__':
    course_id = sys.argv[1]
    for i in range(3):
        try:
            print('开始获取数据...')
            for item in get_test_user():
                print('账户：' + item[0] + ' 密码：' + item[1])
                # 获取题目
                subIds = getSubIds.get_SubID({"Name": item[0], "Password": item[1], "CourseId": course_id})
                for subId in subIds:
                    # 做题
                    answer.get_SubID({"Name": item[0], "Password": item[1], "SubsectionId": subId})

                getAnswers1.run([{'name': item[0], 'password': item[1]}])
            print('数据全部获取完成')
        except Exception as e:
            print(e)
        time.sleep(60 * 6)