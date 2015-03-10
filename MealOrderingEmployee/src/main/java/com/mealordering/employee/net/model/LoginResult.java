package com.mealordering.employee.net.model;

import com.google.api.client.util.Key;

/**
 * Created by Anthonit on 2014/4/21.
 */
public class LoginResult extends Result<LoginResult.Employee> {
    @Override
    public String getMessage() {
        String message = null;
        switch (result) {
            case 0:
                message = "手机号码或密码错误,登录失败.";
                break;
            case 1:
                message = "登录成功!!";
                break;
        }
        return message;
    }

    public static class Employee {
        @Key("EmployeeId")
        private String employeeId;

        public String getEmployeeId() {
            return employeeId;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "employeeId='" + employeeId + '\'' +
                    '}';
        }
    }
}
