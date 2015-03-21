package com.lxq.util;

import java.math.BigDecimal;

public class DecimalUtils {
    private static final int DEF_DIV_SCALE = 10;

    /**
     * ����Param����С����Typeǰ��1��4λ������DotλС��
     *
     * @param sParam
     * @param iDot
     * @param iType��1��ǧ��2����3����
     * @return
     */
    public static String getFormatDecimal(String sParam, int iDot, int iType) {
        double dTemp = 0.0, dParam = 0.0;
        String sReturnValue = sParam;
        if (sParam == null || sParam.trim().length() == 0) return "";
        dParam = Double.parseDouble(sParam);
        switch (iType) {
            case 3:
                dTemp = 100000000;//��
                break;
            case 2:
                dTemp = 10000;//��
                break;
            case 1:
                dTemp = 1000;//ǧ
                break;
            default:
                dTemp = 1;//Ԫ
                break;
        }
        sReturnValue = String.valueOf(divide(dParam, dTemp, iDot));
        return sReturnValue;
    }

    public static String getFormatDecimal(double sParam, int iDot, int iType) {
        return getFormatDecimal(String.valueOf(sParam), iDot, iType);
    }

    public static String getFormatDecimal(String sParam, String iDot, String iType) {
        int iTemp1 = 0, iTemp2 = 0;
        iTemp1 = Integer.parseInt(iDot);
        iTemp2 = Integer.parseInt(iType);
        return getFormatDecimal(sParam, iTemp1, iTemp2);
    }

    /**
     * �ṩ��ȷ�ļӷ����㡣
     *
     * @param dValue1 ������
     * @param dValue2 ����
     * @return ���������ĺ�
     */
    public static double add(double dValue1, double dValue2) {
        BigDecimal bg1 = new BigDecimal(Double.toString(dValue1));
        BigDecimal bg2 = new BigDecimal(Double.toString(dValue2));
        return bg1.add(bg2).doubleValue();
    }

    public static double add(String dValue1, String dValue2) {
        double dTemp1 = 0.0;
        double dTemp2 = 0.0;
        if (dValue1 != null && dValue1.length() != 0) {
            dTemp1 = Double.parseDouble(dValue1);
        }
        if (dValue2 != null && dValue2.length() != 0) {
            dTemp2 = Double.parseDouble(dValue2);
        }
        return add(dTemp1, dTemp2);
    }

    /**
     * �ṩ��ȷ�ļ������㡣
     *
     * @param dValue1 ������
     * @param dValue2 ����
     * @return ���������Ĳ�
     */
    public static double subtract(double dValue1, double dValue2) {
        BigDecimal bg1 = new BigDecimal(Double.toString(dValue1));
        BigDecimal bg2 = new BigDecimal(Double.toString(dValue2));
        return bg1.subtract(bg2).doubleValue();
    }

    public static double subtract(String dValue1, String dValue2) {
        double dTemp1 = 0.0;
        double dTemp2 = 0.0;
        if (dValue1 != null && dValue1.length() != 0) {
            dTemp1 = Double.parseDouble(dValue1);
        }
        if (dValue2 != null && dValue2.length() != 0) {
            dTemp2 = Double.parseDouble(dValue2);
        }
        return subtract(dTemp1, dTemp2);
    }

    /**
     * �ṩ��ȷ�ĳ˷����㡣
     *
     * @param dValue1 ������
     * @param dValue2 ����
     * @return ���������Ļ�
     */
    public static double multiply(double dValue1, double dValue2) {
        BigDecimal bg1 = new BigDecimal(Double.toString(dValue1));
        BigDecimal bg2 = new BigDecimal(Double.toString(dValue2));
        return bg1.multiply(bg2).doubleValue();
    }

    public static double multiply(String dValue1, String dValue2) {
        double dTemp1 = 0.0;
        double dTemp2 = 0.0;
        if (dValue1 != null && dValue1.length() != 0) {
            dTemp1 = Double.parseDouble(dValue1);
        }
        if (dValue2 != null && dValue2.length() != 0) {
            dTemp2 = Double.parseDouble(dValue2);
        }
        return multiply(dTemp1, dTemp2);
    }

    /**
     * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ��
     * С�����Ժ�10λ���Ժ�������������롣
     *
     * @param dValue1 ������
     * @param dValue2 ����
     * @return ������������
     */
    public static double divide(double dValue1, double dValue2) {
        return divide(dValue1, dValue2, DEF_DIV_SCALE);
    }

    public static double divide(String dValue1, String dValue2, int scale) {
        double dTemp1 = 0.0;
        double dTemp2 = 0.0;
        if (dValue1 != null && dValue1.length() != 0) {
            dTemp1 = Double.parseDouble(dValue1);
        }
        if (dValue2 != null && dValue2.length() != 0) {
            dTemp2 = Double.parseDouble(dValue2);
        } else {
            dTemp2 = 1;
        }
        return divide(dTemp1, dTemp2, scale);
    }

    /**
     * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ��Ĭ���������뱣����λС��
     *
     * @param dValue1 ������
     * @param dValue2 ����
     * @param scale   ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
     * @return ������������
     */
    public static double divide(String dValue1, String dValue2) {
        return divide(dValue1, dValue2, 4);
    }

    /**
     * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ
     * �����ȣ��Ժ�������������롣
     *
     * @param dValue1 ������
     * @param dValue2 ����
     * @param scale   ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
     * @return ������������
     */
    public static double divide(double dValue1, double dValue2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal bg1 = new BigDecimal(Double.toString(dValue1));
        BigDecimal bg2 = new BigDecimal(Double.toString(dValue2));
        return bg1.divide(bg2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
