import requests
import result.URL as URL
import result.PythonResult as PythonResult
import sys

session = requests.session()


def getToken(username, password):
    # 登陆账号
    tokenUser = {
        'username': username,  # 账号
        'password': password,  # 密码
        'code': '2341',
        'vid': '',
        'client_id': '43215cdff2d5407f8af074d2d7e589ee',
        'client_secret': 'DBqEL1YfBmKgT9O491J1YnYoq84lYtB/LwMabAS2JEqa8I+r3z1VrDqymjisqJn3',
        'grant_type': 'password',
        'tenant_id': '32'
    }

    respToken = session.post(URL.login_url, data=tokenUser)
    token = respToken.json()
    respToken.close()
    try:
        token['token_type']
    except:
        PythonResult.getResultFalseLogin()
        sys.exit(1)
    return token
