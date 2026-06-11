package com.shashank.portfolio.data.repository

import com.shashank.portfolio.data.source.PortfolioDataSource
import com.shashank.portfolio.domain.model.PortfolioData

/**
 * Repository layer abstracting data access for the presentation layer.
 * Follows clean architecture by keeping domain logic independent of data sources.
 */
class PortfolioRepository(
    private val dataSource: PortfolioDataSource = PortfolioDataSource,
) {
    fun getPortfolioData(): PortfolioData = dataSource.getPortfolioData()
}
