public class ScamInstance {
    // I also wanted to do subCategory as an enum, but there are over **FIVE HUNDRED** 
    // I think it was a string input so people just put what they want.
    
    private String name, url, description, address, reporter;
    private Category category;
    private String subCategory;

    public ScamInstance(String name, String url, Category category, String subCategory, String description, String address, String reporter) {
        this.name = name;
        this.url = url;
        this.category = category;
        this.subCategory = subCategory;
        this.description = description;
        this.address = address;
        this.reporter = reporter;
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

    public String getReporter() {
        return reporter;
    }   

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    private String notNull(String str) {
        return str == "" ? "Not Provided" : str;
    }

    @Override
    public String toString() {
        return "ScamInstance{" 
            +  "\n\tname = " + name       // Prevent people from accidentally clicking on the url
            + ",\n\turl = " + url.replace("http://", "hxxp://").replace("https://", "hxxps://")
            + ",\n\tcategory = " + category         // ^ tria.ge taugh me this
            + ",\n\tsubCategory = " + notNull(subCategory)
            + ",\n\tdescription = " + notNull(description)
            + ",\n\taddress = " + notNull(address)
            + ",\n\treporter = " + notNull(reporter)
            +  "\n}";
    }
}
