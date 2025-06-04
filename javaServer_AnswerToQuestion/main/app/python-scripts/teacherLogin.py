import sys
import io
import os
import globalLogin
import URL

curPath = os.path.abspath(os.path.dirname(__file__))
rootPath = os.path.split(curPath)[0]
sys.path.append(rootPath)

# 确保 Python 的标准输出使用 GBK 编码
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf8')

if __name__ == "__main__":
    globalLogin.login(
        username=sys.argv[1],
        password=sys.argv[2],
        loginUrl=URL.TEACHER_LOGIN_URL,
        infoUrl=URL.TEACHER_INFO_URL,
        identity='teacher'
    )

