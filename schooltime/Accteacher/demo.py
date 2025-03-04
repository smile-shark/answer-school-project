import itertools
str = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
strarr = []
def addArray():
    global strarr
    for r in range(6, 10):  # 生成长度从1到6的组合
        combinations = itertools.product(str, repeat=r)  # 使用itertools.product生成所有可能的组合
        for combination in combinations:
            strarr.append(''.join(combination))  # 将组合转换为字符串并添加到数组中
addArray()
print(strarr[:10])  # 打印数组中的前10个组合作为示例