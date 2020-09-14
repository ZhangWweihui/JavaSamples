package com.zwh.designpattern.prototype;

import com.zwh.designpattern.model.SearchWord;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class PrototypeDemo4 {

    private HashMap<String, SearchWord> currentKeywords=new HashMap<>();
    private long lastUpdateTime = -1;

    public void refresh() {
        // Deep copy
        HashMap<String, SearchWord> newKeywords = new HashMap<>();
        for (HashMap.Entry<String, SearchWord> e : currentKeywords.entrySet()) {
            SearchWord searchWord = e.getValue();
            SearchWord newSearchWord = new SearchWord(
                    searchWord.getKeyword(), searchWord.getCount(), searchWord.getLastUpdateTime());
            newKeywords.put(e.getKey(), newSearchWord);
        }

        // 从数据库中取出更新时间>lastUpdateTime的数据，放入到newKeywords中
        List<SearchWord> toBeUpdatedSearchWords = getSearchWords(lastUpdateTime);
        long maxNewUpdatedTime = lastUpdateTime;
        for (SearchWord searchWord : toBeUpdatedSearchWords) {
            if (searchWord.getLastUpdateTime() > maxNewUpdatedTime) {
                maxNewUpdatedTime = searchWord.getLastUpdateTime();
            }
            if (newKeywords.containsKey(searchWord.getKeyword())) {
                SearchWord oldSearchWord = newKeywords.get(searchWord.getKeyword());
                oldSearchWord.setCount(searchWord.getCount());
                oldSearchWord.setLastUpdateTime(searchWord.getLastUpdateTime());
            } else {
                newKeywords.put(searchWord.getKeyword(), searchWord);
            }
        }

        lastUpdateTime = maxNewUpdatedTime;
        currentKeywords = newKeywords;
    }

    private List<SearchWord> getSearchWords(long lastUpdateTime) {
        // TODO: 从数据库中取出更新时间>lastUpdateTime的数据
        return null;
    }

    /**
     * 先将对象序列化，然后再反序列化成新的对象
     * @param object
     * @return
     */
    public Object deepCopy(Object object) throws IOException,ClassNotFoundException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(object);

        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);

        return oi.readObject();
    }

    public static void main(String[] args) {
        try {
            SearchWord searchWord = new SearchWord("百度", 1234L, System.currentTimeMillis());
            System.out.println(searchWord);
            PrototypeDemo4 pd = new PrototypeDemo4();
            SearchWord searchWord1 = (SearchWord) pd.deepCopy(searchWord);
            System.out.println(searchWord == searchWord1);
            System.out.println(searchWord1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
