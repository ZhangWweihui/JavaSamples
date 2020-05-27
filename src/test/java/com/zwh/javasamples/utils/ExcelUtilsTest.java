package com.zwh.javasamples.utils;

import com.zwh.utils.ExcelUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author 张炜辉
 * @Date 2020/5/27
 */
public class ExcelUtilsTest {

    @Test
    public void test1() throws Exception {
        String path1 = "C:\\Users\\张炜辉\\Desktop\\dlstores.xlsx";
        List<List<String>> list1 = ExcelUtils.readExcel(path1, 0);
        Map<String,DlStore> dlStores = new HashMap<>();
        for(List<String> strings : list1) {
            DlStore dlStore = new DlStore(strings.get(0), strings.get(1), strings.get(2));
            dlStore.setId(dlStore.getId().substring(1));
            dlStores.put(strings.get(2), dlStore);
        }

        String path = "C:\\Users\\张炜辉\\Downloads\\加盟业务数据迁移明细0518给技术.xlsx";
//        Set<String> clueStores = new HashSet<>();
        List<List<String>> list = ExcelUtils.readExcel(path, 0);
////        for(List<String> strings : list) {
////            clueStores.add(strings.get(33));
////        }
////        System.out.println(clueStores.size());
////        System.out.println(clueStores);
//        List<String> sqls = new ArrayList<>();
//        StringBuilder builder = new StringBuilder();
//        for(List<String> strings : list) {
//            String orderNo = strings.get(0);
//            String saleStoreName = strings.get(33);
//            DlStore dlStore = dlStores.get(saleStoreName);
//            String sql = String.format("update `order` set sale_store_id='%s',sale_store_code='%s',sale_store_name='%s' where order_no ='%s';",
//                    dlStore.getId(), dlStore.getCode(), dlStore.getName(), orderNo);
//            sqls.add(sql);
//        }
//        FileUtils.writeLines(new File("C:\\Users\\张炜辉\\Downloads\\sqls0526.txt"), sqls);

        StringBuilder orderNos = new StringBuilder();
        StringBuilder saleStoreNames = new StringBuilder();
        for(List<String> strings : list) {
            String orderNo = strings.get(0);
            String saleStoreName = strings.get(33);
            orderNos.append("'" + orderNo + "',");
            saleStoreNames.append("'" + saleStoreName + "',");
        }
        String sql = String.format("select * from `order` where order_no in (%s) and sale_store_name in (%s);",
                orderNos.toString(), saleStoreNames.toString());
        FileUtils.writeStringToFile(new File("C:\\Users\\张炜辉\\Downloads\\sqls0526_1.txt"), sql);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class DlStore {
        private String id;
        private String code;
        private String name;
    }
}
