package com.raxors.wowbase.ui.creatures.creature_list

import androidx.paging.PagingData
import com.raxors.wowbase.core.base.BaseScreenState
import com.raxors.wowbase.domain.models.creature.CreatureSearch

data class CreatureListScreenState(
    val creatures: PagingData<CreatureSearch> = PagingData.empty(),
    override val isLoading: Boolean = true,
    override val error: Exception? = null
) : BaseScreenState