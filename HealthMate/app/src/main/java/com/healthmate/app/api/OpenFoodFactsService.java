package com.healthmate.app.api;

import com.healthmate.app.model.FoodSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit service interface for the Open Food Facts API
 */
public interface OpenFoodFactsService {
    
    /**
     * Search for food products
     * @param search Query string to search for
     * @param page Page number for pagination
     * @param pageSize Number of results per page
     * @return API response containing food products
     */
    @GET("cgi/search.pl?search_simple=1&action=process&json=1")
    Call<FoodSearchResponse> searchFood(
            @Query("search_terms") String search,
            @Query("page") int page,
            @Query("page_size") int pageSize);
    
    /**
     * Get detailed information about a specific food product
     * @param barcode The barcode or product ID
     * @return API response containing product details
     */
    @GET("api/v0/product/")
    Call<FoodSearchResponse> getFoodDetails(@Query("code") String barcode);
}