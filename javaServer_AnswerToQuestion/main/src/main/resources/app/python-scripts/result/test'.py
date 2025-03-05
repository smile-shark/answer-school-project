str="""insert into question_and_answer (question_id, question, answers)
            values('8e3b584e94fe4dc7827695133cb3e18d','<p><span style="font-family: 宋体;">在</span><span style="font-family: 宋体;">JavaScript中，以下（ ）</span><span style="font-family: 宋体;">属性 返回最后一个子元素节点</span></p>
<p>&nbsp;</p>','该位置为错误答案LBT_1534_LX_5212_WZL_4818<p><span style="font-family: Times New Roman;">firstElementChild</span></p>LBT_1534_LX_5212_WZL_48186d9bfd6226c048b495a0d6755b61b620LBT_1534_LX_
5212_WZL_4818<p><span style="font-family: Times New Roman;">lastElementChild</span></p>LBT_1534_LX_5212_WZL_4818该位置为错误答案LBT_1534_LX_5212_WZL_4818<p><span style="font-family: Times New Roman;">firstChild</span></p>LBT_1534_LX_5212_WZL_4818该位置为错误答案LBT_1534_LX_5212_WZL_4818<p><span style="font-family: 'Times New Roman';">lastChild');
""".replace("'", "\\'")

if __name__ == '__main__':
    print(str)