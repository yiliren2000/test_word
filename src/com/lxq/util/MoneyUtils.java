package com.lxq.util;

import java.text.DecimalFormat;

/**
 * 
 * @author li@zi ������ת��Ϊ��д���
 * 
 */

public class MoneyUtils
{

	public MoneyUtils()
	{
	}

	// �����ָ�ʽ��Ϊ��λС�����ַ���
	public static String round(double value)
	{
		DecimalFormat format = new DecimalFormat("#.##");
		String roundValue = format.format(value);

		if (roundValue.length() - roundValue.indexOf(".") == 2)
			roundValue = roundValue + "0";
		return roundValue;
	}

	// ���������ַ�����ʽ��Ϊ��λС���ַ���
	public static String round(String value)
	{
		double roundValue = Double.valueOf(value.trim()).doubleValue();
		return round(roundValue);
	}

	public static String changeToBig(double value)
	{
		if (value == 0.0D)
		{
			return "��";
		} else
		{
			String svalue = String.valueOf(value);
			return changeToBig(svalue);
		}
	}

	public static String changeToBig(String value)
	{
		if (null == value || "".equals(value.trim()))
			return "��";
		value = round(value);//��������
		String strCheck, strFen, strDW, strNum, strBig, strNow;
		double d = 0;
		try
		{
			d = Double.parseDouble(value);
		} catch (Exception e)
		{
			return "����" + value + "�Ƿ���";
		}

		strCheck = value + ".";
		int dot = strCheck.indexOf(".");
		if (dot > 12)
		{
			return "����" + value + "�����޷�����";
		}

		try
		{
			int i = 0;
			strBig = "";
			strDW = "";
			strNum = "";
			long intFen = (long) (d * 100);
			strFen = String.valueOf(intFen);
			int lenIntFen = strFen.length();
			while (lenIntFen != 0)
			{
				i++;
				switch (i)
				{
				case 1:
					strDW = "��";
					break;
				case 2:
					strDW = "��";
					break;
				case 3:
					strDW = "Ԫ";
					break;
				case 4:
					strDW = "ʰ";
					break;
				case 5:
					strDW = "��";
					break;
				case 6:
					strDW = "Ǫ";
					break;
				case 7:
					strDW = "��";
					break;
				case 8:
					strDW = "ʰ";
					break;
				case 9:
					strDW = "��";
					break;
				case 10:
					strDW = "Ǫ";
					break;
				case 11:
					strDW = "��";
					break;
				case 12:
					strDW = "ʰ";
					break;
				case 13:
					strDW = "��";
					break;
				case 14:
					strDW = "Ǫ";
					break;
				}
				switch (strFen.charAt(lenIntFen - 1))
				// ѡ������
				{
				case '1':
					strNum = "Ҽ";
					break;
				case '2':
					strNum = "��";
					break;
				case '3':
					strNum = "��";
					break;
				case '4':
					strNum = "��";
					break;
				case '5':
					strNum = "��";
					break;
				case '6':
					strNum = "½";
					break;
				case '7':
					strNum = "��";
					break;
				case '8':
					strNum = "��";
					break;
				case '9':
					strNum = "��";
					break;
				case '0':
					strNum = "��";
					break;
				}
				// �����������
				strNow = strBig;
				// ��Ϊ��ʱ�����
				if ((i == 1) && (strFen.charAt(lenIntFen - 1) == '0'))
					strBig = "��";
				// ��Ϊ��ʱ�����
				else if ((i == 2) && (strFen.charAt(lenIntFen - 1) == '0'))
				{ // �Ƿ�ͬʱΪ��ʱ�����
					if (!strBig.equals("��"))
						strBig = "��" + strBig;
				}
				// ԪΪ������
				else if ((i == 3) && (strFen.charAt(lenIntFen - 1) == '0'))
					strBig = "Ԫ" + strBig;
				// ʰ��Ǫ��һλΪ������ǰһλ��Ԫ���ϣ���Ϊ������ʱ����
				else if ((i < 7) && (i > 3)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) != '��')
						&& (strNow.charAt(0) != 'Ԫ'))
					strBig = "��" + strBig;
				// ʰ��Ǫ��һλΪ������ǰһλ��Ԫ���ϣ�ҲΪ������ʱ���
				else if ((i < 7) && (i > 3)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '��'))
				{
				}
				// ʰ��Ǫ��һλΪ������ǰһλ��Ԫ��Ϊ������ʱ���
				else if ((i < 7) && (i > 3)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == 'Ԫ'))
				{
				}
				// ����Ϊ��ʱ���벹������
				else if ((i == 7) && (strFen.charAt(lenIntFen - 1) == '0'))
					strBig = "��" + strBig;
				// ʰ��Ǫ����һλΪ������ǰһλ�������ϣ���Ϊ������ʱ����
				else if ((i < 11) && (i > 7)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) != '��')
						&& (strNow.charAt(0) != '��'))
					strBig = "��" + strBig;
				// ʰ��Ǫ����һλΪ������ǰһλ�������ϣ�ҲΪ������ʱ���
				else if ((i < 11) && (i > 7)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '��'))
				{
				}
				// ʰ��Ǫ����һλΪ������ǰһλΪ��λ��Ϊ������ʱ���
				else if ((i < 11) && (i > 7)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '��'))
				{
				}
				// ��λΪ���Ҵ���Ǫλ��ʮ������ʱ������Ǫ�䲹��
				else if ((i < 11) && (i > 8)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '��')
						&& (strNow.charAt(2) == 'Ǫ'))
					strBig = strNum + strDW + "����"
							+ strBig.substring(1, strBig.length());
				// ����������λ
				else if (i == 11)
				{
					// ��λΪ������ȫΪ�����Ǫλʱ��ȥ����Ϊ��
					if ((strFen.charAt(lenIntFen - 1) == '0')
							&& (strNow.charAt(0) == '��')
							&& (strNow.charAt(2) == 'Ǫ'))
						strBig = "��" + "��"
								+ strBig.substring(1, strBig.length());
					// ��λΪ������ȫΪ�㲻����Ǫλʱ��ȥ����
					else if ((strFen.charAt(lenIntFen - 1) == '0')
							&& (strNow.charAt(0) == '��')
							&& (strNow.charAt(2) != 'Ǫ'))
						strBig = "��" + strBig.substring(1, strBig.length());
					// ��λ��Ϊ������ȫΪ�����Ǫλʱ��ȥ����Ϊ��
					else if ((strNow.charAt(0) == '��')
							&& (strNow.charAt(2) == 'Ǫ'))
						strBig = strNum + strDW + "��"
								+ strBig.substring(1, strBig.length());
					// ��λ��Ϊ������ȫΪ�㲻����Ǫλʱ��ȥ����
					else if ((strNow.charAt(0) == '��')
							&& (strNow.charAt(2) != 'Ǫ'))
						strBig = strNum + strDW
								+ strBig.substring(1, strBig.length());
					// �����������
					else
						strBig = strNum + strDW + strBig;
				}
				// ʰ�ڣ�Ǫ����һλΪ������ǰһλ�������ϣ���Ϊ������ʱ����
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) != '��')
						&& (strNow.charAt(0) != '��'))
					strBig = "��" + strBig;
				// ʰ�ڣ�Ǫ����һλΪ������ǰһλ�������ϣ�ҲΪ������ʱ���
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '��'))
				{
				}
				// ʰ�ڣ�Ǫ����һλΪ������ǰһλΪ��λ��Ϊ������ʱ���
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) == '0')
						&& (strNow.charAt(0) == '��'))
				{
				}
				// ��λΪ���Ҳ�����Ǫ��λ��ʮ������ʱȥ���ϴ�д�����
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) != '0')
						&& (strNow.charAt(0) == '��')
						&& (strNow.charAt(1) == '��')
						&& (strNow.charAt(3) != 'Ǫ'))
					strBig = strNum + strDW
							+ strBig.substring(1, strBig.length());
				// ��λΪ���Ҵ���Ǫ��λ��ʮ������ʱ������Ǫ��䲹��
				else if ((i < 15) && (i > 11)
						&& (strFen.charAt(lenIntFen - 1) != '0')
						&& (strNow.charAt(0) == '��')
						&& (strNow.charAt(1) == '��')
						&& (strNow.charAt(3) == 'Ǫ'))
					strBig = strNum + strDW + "����"
							+ strBig.substring(2, strBig.length());
				else
					strBig = strNum + strDW + strBig;
				strFen = strFen.substring(0, lenIntFen - 1);
				lenIntFen--;
			}
			return strBig;
		} catch (Exception e)
		{
			return "";
		}
	}
}
