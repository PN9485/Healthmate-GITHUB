package com.healthmate.app.model;

import com.google.gson.annotations.SerializedName;
import com.healthmate.app.model.api.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Response model for food search API
 */
public class FoodSearchResponse {
    
    @SerializedName("count")
    private int count;
    
    @SerializedName("page")
    private int page;
    
    @SerializedName("page_size")
    private int pageSize;
    
    @SerializedName("products")
    private List<Product> products;
    
    @SerializedName("product")
    private Product singleProduct;
    
    // Getters and setters
    public int getCount() {
        return count;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public List<Product> getProducts() {
        if (products == null) {
            products = new ArrayList<>();
            
            // If we have a single product response, add it to the list
            if (singleProduct != null) {
                products.add(singleProduct);
            }
        }
        return products;
    }
    
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
    public Product getSingleProduct() {
        return singleProduct;
    }
    
    public void setSingleProduct(Product singleProduct) {
        this.singleProduct = singleProduct;
    }
    
    /**
     * Convert API response to a list of Food objects
     * @return List of Food objects
     */
    public List<Food> toFoodList() {
        List<Food> foodList = new ArrayList<>();
        
        for (Product product : getProducts()) {
            // Skip products without nutritional information
            if (product.getNutriments() != null) {
                foodList.add(product.toFood());
            }
        }
        
        return foodList;
    }
}