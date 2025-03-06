import subprocess
import pymysql
import getAnswers1
import MysqlConfig

if __name__ == '__main__':
    try:
        print('数据库连接中...')
        db = pymysql.connect(
            host=MysqlConfig.host,
            password=MysqlConfig.password,
            user=MysqlConfig.user,
            database=MysqlConfig.database
        )
        print('连接成功...')
        cursor = db.cursor()
        db.ping(reconnect=True)
        print('开始查询数据...')
        cursor.execute(f"select user_id,user_password from user")
        print('获取到数据:')
        result = cursor.fetchall()
        print('开始获取数据...')
        for item in result:
            print('账户：' + item[0] + ' 密码：' + item[1])
            getAnswers1.run([{'name': item[0], 'password': item[1]}])
        print('数据全部获取完成')
    except Exception as e:
        print(e)
