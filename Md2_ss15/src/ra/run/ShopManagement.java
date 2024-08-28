package ra.run;

import ra.entity.Categories;
import ra.entity.Product;

import java.util.*;

public class ShopManagement {
    public static List<Categories> categoriesList = new ArrayList<>();
    public static List<Product> productList = new ArrayList<>();

    static  {
        Categories cat = new Categories(1, "giay", "giay danh rieng cho nam",true);
        Categories cat2 = new Categories(2, "Dep","Dep danh rieng cho nu", true);
        Categories cat3 = new Categories(3, "Mu", "Mu danh rieng cho nu", true);

        categoriesList.add(cat);
        categoriesList.add(cat2);
        categoriesList.add(cat3);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("*****************SHOP MENU****************");
            System.out.println("*                                        *");
            System.out.println("*     1. Quản lý danh mục sản phẩm       *");
            System.out.println("*     2. Quản lý sản phẩm                *");
            System.out.println("*     3. Thoát                           *");
            System.out.println("*                                        *");
            System.out.println("******************************************");
            System.out.print("Lựa chọn của bạn:");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    displayCategoriesMenu(scanner);
                    break;
                case 2:
                    displayProductMenu(scanner);
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.err.println("Vui lòng chọn từ 1-3");
            }
        } while (true);
    }

    public static void displayCategoriesMenu(Scanner scanner) {
        boolean isExit = true;
        do {
            System.out.println("*****************CATEGORIES MENU***************");
            System.out.println("*                                             *");
            System.out.println("*     1. Nhập thông tin các danh mục          *");
            System.out.println("*     2. Hiển thị thông tin danh mục          *");
            System.out.println("*     3. Cập nhật thông tin danh mục          *");
            System.out.println("*     4. Xóa danh mục                         *");
            System.out.println("*     5. Cập nhật trạng thái danh mục         *");
            System.out.println("*     6. Thoát                                *");
            System.out.println("*                                             *");
            System.out.println("***********************************************");
            System.out.print("Lựa chọn của bạn:");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    inputListCategory(scanner);
                    break;
                case 2:
                    handleShowListCategory();
                    break;
                case 3:
                    handleUpdateCategory(scanner);
                    break;
                case 4:
                    handleDeleteCategory(scanner);
                    break;
                case 5:
                    inputUpdateStatus(scanner);
                    break;
                case 6:
                    isExit = false;
                    break;
                default:
                    System.err.println("Vui lòng nhập từ 1-6");
            }
        } while (isExit);
    }

    public static void inputListCategory (Scanner scanner) {
        System.out.println("Nhập số danh mục sản phẩm: ");
        int countCategory = Integer.parseInt(scanner.nextLine());
        for (int i = 0 ; i < countCategory ; i++) {
            Categories category = new Categories();
            category.inputData(scanner);
            categoriesList.add(category);
        }
        System.out.println("Đã thêm thành công " + countCategory + " danh mục");
    }

    public static void handleShowListCategory () {
        if (categoriesList.isEmpty()) {
            System.err.println("Danh mục trống");
            return;
        }
        for (Categories category : categoriesList) {
            category.displayData();
        }
    }

    public static int findIndexCategoryById (Integer id) {
        for (int i = 0; i < categoriesList.size(); i++) {
            if (categoriesList.get(i).getCatalogId() == id) {
                return i;
            }
        }
        return -1;
    }

    public static void handleUpdateCategory(Scanner scanner) {
        System.out.println("Chọn danh mục muốn cập nhật theo Id: ");
        int updateCateId = Integer.parseInt(scanner.nextLine());
        int indexUpdate = findIndexCategoryById(updateCateId);
        if (indexUpdate >= 0) {
            Categories categoryUpdate = categoriesList.get(indexUpdate);
            boolean isLoop = true;
            do {
                System.out.println("1. Cap nhat ten danh muc");
                System.out.println("2. Cap nhat mo ta danh muc");
                System.out.println("3. Cap nhat trang thai danh muc");
                System.out.println("4. Thoat");
                System.out.println("Lua chon cua ban");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.println("Tên danh mục muốn sửa: ");
                        categoryUpdate.setCatalogName(scanner.nextLine());
                        break;
                    case 2:
                        System.out.println("Mô tả danh mục muốn sửa: ");
                        categoryUpdate.setDescription(scanner.nextLine());
                        break;
                    case 3:
                        System.out.println("Trạng thái danh mục mốn sửa: ");
                        categoryUpdate.setCatalogStatus(scanner.nextBoolean());
                        break;
                    default:
                        isLoop =false;
                }
            }
            while (isLoop);
        }
        else {
            System.err.println("Ma danh muc san pham khong ton tai");
        }
    }

    public static void handleDeleteCategory(Scanner scanner) {
        System.out.println("Nhap vao danh muc san pham can xoa:");
        int catalogId = Integer.parseInt(scanner.nextLine());
        int indexDelete = findIndexCategoryById(catalogId);
        if (indexDelete >= 0) {
            boolean isProductInCategory = false;
            for (Product product : ShopManagement.productList) {
                if (product.getCatalogId() == catalogId) {
                    isProductInCategory = true;
                    break;
                }
            }
            if (isProductInCategory) {
                System.err.println("Danh mục đã chứa sản phẩm, không thể xóa được");
            } else {
                for (int i = indexDelete; i < ShopManagement.categoriesList.size() - 1; i++) {
                    ShopManagement.categoriesList.set(i, ShopManagement.categoriesList.get(i + 1));
                }
                ShopManagement.categoriesList.remove(ShopManagement.categoriesList.size() - 1);
                System.out.println("Đã xóa danh mục thành công.");
            }
        }
        else {
            System.err.println("Mã danh mục không tồn tại");
        }
    }


    public static void inputUpdateStatus(Scanner scanner) {
        System.out.println("Mời bạn nhập mã danh mục cần thay đổi trạng thái: ");
        int categoryId = Integer.parseInt(scanner.nextLine());
        int indexCategory = findIndexCategoryById(categoryId);
        if (indexCategory == -1) {
            System.err.println("Không tìm thấy danh mục sản phẩm có mã " + categoryId);
        } else {
            Categories category = categoriesList.get(indexCategory);
            category.setCatalogStatus(!category.getCatalogStatus());
            System.out.println("Đã thay đổi trạng thái của sản phẩm " + category.getCatalogId() + " thành " + category.getCatalogStatus());
        }

    }



    public static void displayProductMenu(Scanner scanner) {
        boolean isExit = true;
        do {
            System.out.println("**********************************PRODUCTS MENU******************************");
            System.out.println("*                                                                           *");
            System.out.println("*       1. Nhập thông tin các sản phẩm                                      *");
            System.out.println("*       2. Hiển thị thông tin các sản phẩm                                  *");
            System.out.println("*       3. Sắp xếp các sản phẩm theo giá                                    *");
            System.out.println("*       4. Cập nhật thông tin các sản phẩm theo mã sản phảm                 *");
            System.out.println("*       5. Xóa sản phẩm theo mã sản phảm                                    *");
            System.out.println("*       6. Tìm kiếm các sản phẩm theo tên sản phẩm                          *");
            System.out.println("*       7. Tìm kiếm sản phẩm theo khoảng giá a-b (a, b nhập từ bàn phím)    *");
            System.out.println("*       8. Thoát                                                            *");
            System.out.println("*                                                                           *");
            System.out.println("*****************************************************************************");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    inputListProduct(scanner);
                    break;
                case 2:
                    displayListProduct();
                    break;
                case 3:
                    sortAsxendingProduct();
                    break;
                case 4:
                    updateProduct(scanner);
                    break;
                case 5:
                    deleteProduct(scanner);
                    break;
                case 6:
                    searchProductByName(scanner);
                    break;
                case 7:
                    searchProductByPrice(scanner);
                    break;
                case 8:
                    isExit = false;
                    break;
                default:
                    System.err.println("Vui long nhap tu 1-8");
            }
        } while (isExit);
    }

    public static void searchProductByPrice(Scanner scanner) {
        System.out.println("Nhập khoảng giá cần tìm kiếm:");
        System.out.print("Giá bắt đầu = ");
        float fromPrice = Float.parseFloat(scanner.nextLine());
        System.out.print("Giá kết thúc = ");
        float toPrice = Float.parseFloat(scanner.nextLine());

        int countProduct = 0;
        System.out.printf("Các sản phẩm trong khoảng giá [%.2f - %.2f] là:\n", fromPrice, toPrice);

        for (Product product : ShopManagement.productList) {
            if (product.getPrice() >= fromPrice && product.getPrice() <= toPrice) {
                product.displayData();
                countProduct++;
            }
        }

        System.out.printf("Có %d sản phẩm được tìm thấy trong khoảng giá [%.2f - %.2f]\n", countProduct, fromPrice, toPrice);
    }

    public static void searchProductByName(Scanner scanner) {
        System.out.println("Nhập ten sản phẩm cần tìm kiếm: ");
        String inputProductName = scanner.nextLine().toLowerCase();
        boolean found = false;
        for (Product product : productList) {
            if (product.getProductName().toLowerCase().contains(inputProductName)) {
                product.displayData();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy sản phẩm có tên là '" + inputProductName + "'.");
        }
    }

    public static void deleteProduct(Scanner scanner) {
        System.out.println("Mời bạn nhập sản phẩm cần xóa:");
        String productId = scanner.nextLine();
        int indexProduct = findIndexProductById(productId);

        if (indexProduct >= 0) {
            Product productToDelete = ShopManagement.productList.get(indexProduct);
            System.out.println("Đã xóa sản phẩm: " + productToDelete.toString());
            ShopManagement.productList.remove(indexProduct);
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID: " + productId);
        }
    }

    public static int findIndexProductById(String productId) {
        for (int i = 0; i < productList.size(); i++) {
            if(productList.get(i).getProductId().equals(productId)){
                return i;
            }
        }
        return -1;
    }

    //Cập nhật thông tin các sản phẩm theo mã sản phảm
    public static void updateProduct(Scanner scanner) {
        System.out.println("Nhập mã sản phẩm cần cập nhật: ");
        String productId = scanner.nextLine();
        int indexProduct = findIndexProductById(productId);

        if (indexProduct >= 0) {
            Product productToUpdate = ShopManagement.productList.get(indexProduct);
            productToUpdate.updateData(scanner);
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID: " + productId);
        }
    }

    public static void sortAsxendingProduct() {
        productList.sort(Comparator.comparingDouble(Product::getPrice));
        System.out.println("Danh sách sản phẩm sau khi sắp xếp");
        for (Product product : productList) {
            product.displayData();
        }
    }

    public static void displayListProduct() {
        for (Product product : productList) {
            product.displayData();
        }
    }

    public static void inputListProduct(Scanner scanner) {
        System.out.println("Nhập số sản phẩm: ");
        int countProduct = Integer.parseInt(scanner.nextLine());
        for (int i = 0 ; i < countProduct ; i++) {
            Product product = new Product();
            product.inputData(scanner);
            productList.add(product);
        }
        System.out.println("Đã thêm thành công " + countProduct + " sản phẩm");

    }

}