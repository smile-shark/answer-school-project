import requests
import sys
import PythonResult as Result
import URL


def login(username, password, loginUrl, infoUrl, identity='student'):
    try:
        loginData = {
            'username': username,
            'password': password,
            'code': '2341',
            'client_id': 'c12abe723eda4b66af77015f2b572440',
            'client_secret': 'yHpq/AII2pBeUrUlSeMZhEs84gxSfQ/y+PyGBOmI6dh33EK6Za1VwHwz7uRRifUC',
            'grant_type': 'password',
            'tenant_id': '32'
        } if identity == 'teacher' else {
            'username': username,
            'password': password,
            'code': '2341',
            'vid': '',
            'client_id': '43215cdff2d5407f8af074d2d7e589ee',
            'client_secret': 'DBqEL1YfBmKgT9O491J1YnYoq84lYtB/LwMabAS2JEqa8I+r3z1VrDqymjisqJn3',
            'grant_type': 'password',
            'tenant_id': '32'
        }
        resp = requests.post(loginUrl, data=loginData)
        if resp.status_code != 200:
            Result.getResultFalseLogin()
            sys.exit(1)
        token = resp.json()
        resp.close()
        if 'token_type' not in token or 'access_token' not in token:
            Result.getResultFalseLogin()
            sys.exit(1)
        if identity == 'teacher':
            headers = URL.Headers.TEACHER_INFO_HEADERS(token)
            resp = requests.get(infoUrl, headers=headers)
            if resp.status_code != 200:
                Result.getResultFalseLogin()
                sys.exit(1)
            teacher_info = resp.json()
            resp.close()
            if 'data' not in teacher_info or 'accountName' not in teacher_info['data']:
                Result.getResultFalseLogin()
                sys.exit(1)
            Result.getResultTrueLogin(username, password, teacher_info['data']['accountName'])
        else:
            headers = URL.Headers.STUDENT_INFO_HEADERS(token)
            resp = requests.get(infoUrl, headers=headers)
            if resp.status_code != 200:
                Result.getResultFalseLogin()
                sys.exit(1)
            student_info = resp.json()
            resp.close()
            if 'data' not in student_info or 'stuInfo' not in student_info['data'] or 'studentName' not in \
                    student_info['data']['stuInfo']:
                Result.getResultFalseLogin()
                sys.exit(1)
            Result.getResultTrueLogin(username, password, student_info['data']['stuInfo']['studentName'])
    except Exception as e:
        Result.getResultFalseLogin()
        sys.exit(1)
