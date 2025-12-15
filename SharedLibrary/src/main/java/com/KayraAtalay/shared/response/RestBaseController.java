package com.KayraAtalay.shared.response;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.KayraAtalay.shared.utils.PageableRequest;
import com.KayraAtalay.shared.utils.PagerUtil;
import com.KayraAtalay.shared.utils.RestPageableEntity;

public class RestBaseController {

    public Pageable toPageable(PageableRequest request) {
        return PagerUtil.toPageable(request);
    }

    public <T> RestPageableEntity<T> toPageableResponse(Page<?> page, List<T> content) {

        return PagerUtil.toPageableResponse(page, content);

    }

    public <T> RootEntity<T> ok(T payload) {
        return RootEntity.ok(payload);
    }

}