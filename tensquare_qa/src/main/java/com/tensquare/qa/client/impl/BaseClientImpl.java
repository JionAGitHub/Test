package com.tensquare.qa.client.impl;

import com.tensquare.qa.client.BaseClient;
import entity.Result;
import entity.StatusCode;

public class BaseClientImpl implements BaseClient {

    @Override
    public Result findAllById(String labelId) {
        return new Result(false, StatusCode.ERROR,"熔断器触发了！");
    }
}
