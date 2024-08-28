package ra.entity;

import ra.run.ShopManagement;

import java.io.BufferedReader;
import java.util.Scanner;

public class Categories {
    private int catalogId;
    private String catalogName;
    private String description;
    private Boolean catalogStatus;

    public Categories() {
    }

    public Categories(int catalogId, String catalogName, String description, Boolean catalogStatus) {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
        this.description = description;
        this.catalogStatus = catalogStatus;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCatalogStatus() {
        return catalogStatus;
    }

    public void setCatalogStatus(Boolean catalogStatus) {
        this.catalogStatus = catalogStatus;
    }


    public int inputCatalogId () {
        int MaxId = 0;
        for (Categories categories : ShopManagement.categoriesList) {
            if (categories.getCatalogId() > MaxId) {
                MaxId = categories.getCatalogId();
            }
        }
        return MaxId + 1;
    }

    public String inputCatalogName (Scanner scanner) {
        do {
            System.out.println("Mời bạn nhập tên danh mục: ");
            String catalogName = scanner.nextLine();

            if (catalogName.isEmpty() || catalogName.length() > 50) {
                System.err.println("Tên danh mục không hợp lệ, vui lòng nhập lại.");
                continue;
            }

            boolean isExist = false;
            for (Categories category : ShopManagement.categoriesList) {
                if (category.getCatalogName().equalsIgnoreCase(catalogName)) {
                    isExist = true;
                    break;
                }
            }

            if (isExist) {
                System.err.println("Tên danh mục đã tồn tại, vui lòng nhập lại.");
            } else {
                return catalogName;
            }
        } while (true);
    }

    public String inputDescription (Scanner scanner) {
        System.out.println("Mời bạn nhập vào mô tả danh mục: ");
        return scanner.nextLine();
    }

    public Boolean inputCatalogStatus (Scanner scanner) {
        System.out.println("Mời nhập vào trạng thái danh mục: ");
        do {
            String catalogStatus = scanner.nextLine();
            if (catalogStatus.equals("true") || catalogStatus.equals("false")) {
                return Boolean.parseBoolean(scanner.nextLine());
            }
            else {
                System.err.println("Trạng thái danh mục chỉ nhận giá trị true | false, vui lòng nhập lại");
            }
        } while (true);
    }

    public void inputData(Scanner scanner) {
        this.catalogId = inputCatalogId();
        this.catalogName = inputCatalogName(scanner);
        this.description = inputDescription(scanner);
        this.catalogStatus = inputCatalogStatus(scanner);
    }

    public void displayData() {
        System.out.printf(" Mã danh mục: %d  |   Tên danh mục: %s  |   Mô tả: %s   |   Trạng thái: %s \n",
                this.catalogId, this.catalogName, this.description, this.catalogStatus ? "hoạt động" : " không hoạt động"
        );
        System.out.println("----------------------------------------------------------------------------");
    }
}