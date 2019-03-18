package xyz.manolos.cubos.search

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = [SearchResultsModule::class])
interface SearchResultsComponent {

    fun inject(activity: SearchResultsActivity)
}

@Module
class SearchResultsModule(private val searchResultsView: SearchResultsView) {

    @Provides
    fun provideSearchResults() = searchResultsView
}