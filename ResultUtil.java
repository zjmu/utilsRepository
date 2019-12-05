package com.ucarinc.usercenter.bxaftersale.utils;


import com.ucarinc.usercenter.bxaftersale.domain.PageDataVO;
import com.ucarinc.usercenter.bxaftersale.domain.PageInfo;
import com.ucarinc.usercenter.bxaftersale.domain.Pager;
import com.ucarinc.usercenter.bxaftersale.domain.Result;
import com.ucarinc.usercenter.bxaftersale.enums.BaseEnum;

import java.util.List;

/**
 * @author lidinglin
 * @date 2019/11/11
 * <p>
 * Description:响应结果通用封装方法
 */
public class ResultUtil {
    @SuppressWarnings("unchecked")
    private static final Result SUCCESS = new Result(BaseEnum.SUCCESS.getCode(), BaseEnum.SUCCESS.getMessage(), null);

    private ResultUtil() {
    }

    /**
     * 成功响应
     */
    public static <T> Result<T> success(int code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static Result success(int code, String message) {
        return ResultUtil.success(code, message, null);
    }

    public static <T> Result<T> success(T data) {
        return ResultUtil.success(BaseEnum.SUCCESS.getCode(), BaseEnum.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> success(String message, T data) {
        return ResultUtil.success(BaseEnum.SUCCESS.getCode(), message, data);
    }

    public static Result success() {
        return SUCCESS;
    }

    public static <T> Result<PageDataVO<T>> success(int code, String message, List<T> data, Long total, Pager pager) {
        PageDataVO<T> pageDataVO = new PageDataVO<>();
        PageInfo page = new PageInfo();
        page.setPageNo(pager.getPageNo() + 1);
        page.setPageSize(pager.getPageSize());
        page.setTotalCount(total);
        page.setTotalPageCount((total + pager.getPageSize() - 1) / pager.getPageSize());

        pageDataVO.setPageInfo(page);
        pageDataVO.setPageList(data);
        return new Result<>(code, message, pageDataVO);
    }

    public static <T> Result<PageDataVO<T>> success(List<T> data, Long total, Pager pager) {
        return ResultUtil.success(BaseEnum.SUCCESS.getCode(), BaseEnum.SUCCESS.getMessage(), data, total, pager);
    }

    /**
     * 错误响应
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(BaseEnum.FAILED.getCode(), message, null, null);
    }

    public static <T> Result<T> error(int code, String message, T data, Throwable e) {
        return new Result<>(code, message, data, e);
    }

    public static Result error(BaseEnum baseEnum) {
        return new Result<>(baseEnum.getCode(), baseEnum.getMessage(), null, null);
    }

    public static Result error(BaseEnum baseEnum, String message, Throwable e) {
        return new Result<>(baseEnum.getCode(), message, null, e);
    }
}
