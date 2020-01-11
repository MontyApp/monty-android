package com.monty.domain

import com.monty.data.model.ui.Category
import com.monty.data.model.ui.type.Categories
import com.monty.domain.base.BaseSingler
import io.reactivex.Single
import javax.inject.Inject

class GetCategoriesSingler @Inject constructor() : BaseSingler<List<Category>>() {

    override fun create(): Single<List<Category>> {
        return Single.just(Categories.list)
    }
}
