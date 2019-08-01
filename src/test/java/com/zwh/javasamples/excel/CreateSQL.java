package com.zwh.javasamples.excel;

import com.alibaba.fastjson.JSON;
import com.zwh.utils.ExcelUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * @author ZhangWeihui
 * @date 2019/7/15 11:44
 */
public class CreateSQL {

    private static final String SQL = "update t_supplier set bank_account = '#{bankAccount}',bank_id = #{bankId},bank_branch = '#{bankBranch}'," +
            "bank_type = #{bankType},receiver = '#{receiver}',bank_address = '#{bankAddress}' where code = '#{code}';";

    public static final String FILE_PATH = "C:\\Users\\admin\\Documents\\全国供应商明细核对-已核对更新(2).xls";

    public static final String OUT_FILE_PATH = "C:\\Users\\admin\\Documents\\supplier_sqls.txt";

    public static void main(String[] args) {
        CreateSQL.action();
    }

    public static void action(){
        try {
            List<List<Object>> dataList = ExcelUtils.getData(FILE_PATH, 1,1, 852, 9);
            System.out.println("数据行数：" + dataList.size());
            List<String> content = new ArrayList<>();
            Map<String,Integer> bankNameIdMap = getBankNameIdMap();
            for(List<Object> objects : dataList) {
               System.out.println(StringUtils.arrayToCommaDelimitedString(objects.toArray()));
               String bankAccount = (String)objects.get(3);
               bankAccount = bankAccount.trim();
               if(bankAccount.startsWith("_")){
                   bankAccount = bankAccount.substring(1);
               }
               String sql = SQL.replace("#{bankAccount}",bankAccount);

               String bankName = (String)objects.get(4);
               Integer bankId = bankNameIdMap.get(bankName.trim());
               if(bankId==null) {
                   throw new IllegalArgumentException(bankName);
               }
               sql = sql.replace("#{bankId}", bankId.toString());

               String bankBranck = (String)objects.get(5);
               sql = sql.replace("#{bankBranch}", bankBranck.trim());

               String bankTypeName = (String)objects.get(6);
               int bankType = getAccountType(bankTypeName);
               sql = sql.replace("#{bankType}", String.valueOf(bankType));

               String receiver = (String)objects.get(7);
               sql = sql.replace("#{receiver}", StringUtils.hasText(receiver) ? receiver.trim() : "");

               String bankAddress = (String)objects.get(8);
               sql = sql.replace("#{bankAddress}", bankAddress.trim());

               String code = (String)objects.get(1);
               sql = sql.replace("#{code}", code.trim());
               content.add(sql);
           }
            FileUtils.writeLines(new File(OUT_FILE_PATH), content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String,Integer> getBankNameIdMap() throws Exception {
        List<String> lines = FileUtils.readLines(new File("C:\\Users\\admin\\Documents\\banks.txt"));
        Map<String,Integer> map = new HashMap<>();
        lines.forEach(l -> {
            String[] strs = StringUtils.commaDelimitedListToStringArray(l);
            map.put(strs[1].trim(), Integer.valueOf(strs[0].trim()));
        });
        return map;
    }

    private static int getAccountType(String accountTypeName){
        return accountTypeName.trim().equals("个人") ? 1 : 2;
    }
}
