package com.bangkit2024.watch_list.di

import android.content.Context
import com.bangkit2024.moviesubmissionexpert.di.WatchListModuleDependencies
import com.bangkit2024.watch_list.ui.WatchListFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [WatchListModuleDependencies::class])
interface WatchListComponent {

    fun inject(watchListFragment: WatchListFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(watchListModuleDependencies: WatchListModuleDependencies): Builder
        fun build(): WatchListComponent
    }

}