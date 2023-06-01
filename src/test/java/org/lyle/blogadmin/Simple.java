package org.lyle.blogadmin;

public class Simple {
    public static void main(String[] args) {


        String sql = "where 1=1";
        String a = "SELECT ab,c";
        StringBuffer sb = new StringBuffer();
        String[] arr = a.split(",");



        if (a.contains(",")) {

            for(int i = 0;i<arr.length;i++){
                System.out.println("===="+arr[i]);
            }

            sb.append(arr[0]);
            sb.append(" ");
        } else {
            sb.append(sql);
        }

        System.out.println(sb.toString());

    }
}
