package com.smileShark.main.code;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:python-scripts-path.properties")
public class PythonPath {
    @Value("${LOGIN}")
    public String PYTHON_SCRIPTS_PATH_LOGIN;
    @Value("${GTE_SOME_ID}")
    public String PYTHON_SCRIPTS_PATH_GTE_SOME_COURSE_ID;
    @Value("${GET_COUNT_USE_SUBSECTION}")
    public String PYTHON_SCRIPTS_PATH_GET_COUNT_USE_SUBSECTION;
    @Value("${GET_COUNT_USE_CHAPTER}")
    public String PYTHON_SCRIPTS_PATH_GET_COUNT_USE_CHAPTER;
    @Value("${GET_COUNT_USE_COURSE_ID}")
    public String PYTHON_SCRIPTS_PATH_GET_COUNT_USE_COURSE_ID;
    @Value("${GET_COUNT_ALL}")
    public String PYTHON_SCRIPTS_PATH_GET_COUNT_ALL;
    @Value("${GET_ANSWERS_1}")
    public String PYTHON_SCRIPTS_PATH_GET_ANSWERS_1;
    @Value("${ANSWER_QUESTION_BY_SECTION}")
    public String PYTHON_SCRIPTS_PATH_ANSWER_QUESTION_BY_SECTION;
    @Value("${GET_NEW_ANSWERS}")
    public String PYTHON_SCRIPTS_PATH_GET_NEW_ANSWERS;
}
