package com.cheezhi.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.widget.Toast;

public class MyMethod {
	// ��ʾ��ʾ
	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	// ����idת��������
	public static String areaName(int num) {
		String string = null;
		switch (num) {
		case 0:
			string = "不限";
			break;
		case 1:
			string = "东  1B";
			break;
		case 4:
			string = "东  1A";
			break;
		case 2:
			string = "西  1";
			break;
		case 3:
			string = "西  2";
			break;
		default:
			break;
		}
		return string;
	}

	// ����idת��������
	public static String nameName(int num) {
		String string = null;
		switch (num) {
		case 0:
			string = "不限";
			break;
		case 3:
			string = "多媒体";
			break;
		case 1:
			string = "普通";
			break;
		case 2:
			string = "多媒体&考研";
			break;
		default:
			break;
		}
		return string;
	}

	// ����idת��������
	public static String sortName(int num) {
		String string = null;
		switch (num) {
		case 0:
			string = "数量最多";
			break;
		case 1:
			string = "数量最少";
			break;
		default:
			break;
		}
		return string;
	}

	public static String timeType1() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}
}
