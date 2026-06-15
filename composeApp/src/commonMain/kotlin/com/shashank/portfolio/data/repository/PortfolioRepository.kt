package com.shashank.portfolio.data.repository

import com.shashank.portfolio.data.remote.PortfolioApiClient
import com.shashank.portfolio.data.source.PortfolioDataSource
import com.shashank.portfolio.domain.model.PortfolioData

/**
 * Repository layer abstracting data access for the presentation layer.
 * Serves bundled data immediately and can refresh from the Ktor API when available.
 */
class PortfolioRepository(
    private val dataSource: PortfolioDataSource = PortfolioDataSource,
    private val apiClient: PortfolioApiClient = PortfolioApiClient(),
) {
    fun getLocalPortfolioData(): PortfolioData = dataSource.getPortfolioData()

    suspend fun fetchRemotePortfolio(): PortfolioData? = apiClient.fetchPortfolio()
}
