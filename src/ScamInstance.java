public class ScamInstance {
    public enum Category {
        Phishing,
        Scamming,
        Malware,
        Hacked
    }
    // I also wanted to do subCategory as an enum, but there are over **FIVE HUNDRED** 
    // I think it was a string input so people just put what they want.
    
    private String name, url, description, address;
    private Category category;
    private String subCategory;

    public ScamInstance(String name, String url, Category category, String subCategory, String description, String address) {
        this.name = name;
        this.url = url;
        this.category = category;
        this.subCategory = subCategory;
        this.description = description;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
}
