package com.shashank.portfolio.data.remote

import com.shashank.portfolio.domain.model.PortfolioData

class PortfolioApiClient {
    suspend fun fetchPortfolio(): PortfolioData? = fetchPortfolioRemote()
}

internal expect suspend fun fetchPortfolioRemote(): PortfolioData?

internal expect fun portfolioApiBaseUrl(): String
