import subprocess
import pymysql


try:
    print('数据库连接中...')
    db=pymysql.connect(
        host='localhost',
        password='123456',
        user='root',
        database='newmysqlbase'
    )
    print('连接成功...')
    cursor=db.cursor()
    db.ping(reconnect=True)
    print('开始查询数据...')
    cursor.execute(f"select userId,userPas from user")
    print('获取到数据:')
    result=cursor.fetchall()
    for item in result:
        print('账户：'+item[0]+' 密码：'+item[1])
    print('开始获取数据...')
    for item in result:
       print('开始获取账户：'+item[0]+' 的数据...')
       subprocess.run(['python','getAnswers1.py',item[0],item[1]],capture_output=True,text=True)
       print('获取账户：'+item[0]+' 的数据完成')
    print('数据全部获取完成')
    
except Exception as e:
    print(e)

