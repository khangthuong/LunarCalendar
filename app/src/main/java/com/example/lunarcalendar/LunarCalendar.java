package com.example.lunarcalendar;

import com.example.lunarcalendar.model.LunarDate;

import java.util.ArrayList;

public class LunarCalendar {

    public static final Integer[] TK19 = new Integer[]{
            0x30baa3, 0x56ab50, 0x422ba0, 0x2cab61, 0x52a370, 0x3c51e8, 0x60d160, 0x4ae4b0, 0x376926, 0x58daa0,
            0x445b50, 0x3116d2, 0x562ae0, 0x3ea2e0, 0x28e2d2, 0x4ec950, 0x38d556, 0x5cb520, 0x46b690, 0x325da4,
            0x5855d0, 0x4225d0, 0x2ca5b3, 0x52a2b0, 0x3da8b7, 0x60a950, 0x4ab4a0, 0x35b2a5, 0x5aad50, 0x4455b0,
            0x302b74, 0x562570, 0x4052f9, 0x6452b0, 0x4e6950, 0x386d56, 0x5e5aa0, 0x46ab50, 0x3256d4, 0x584ae0,
            0x42a570, 0x2d4553, 0x50d2a0, 0x3be8a7, 0x60d550, 0x4a5aa0, 0x34ada5, 0x5a95d0, 0x464ae0, 0x2eaab4,
            0x54a4d0, 0x3ed2b8, 0x64b290, 0x4cb550, 0x385757, 0x5e2da0, 0x4895d0, 0x324d75, 0x5849b0, 0x42a4b0,
            0x2da4b3, 0x506a90, 0x3aad98, 0x606b50, 0x4c2b60, 0x359365, 0x5a9370, 0x464970, 0x306964, 0x52e4a0,
            0x3cea6a, 0x62da90, 0x4e5ad0, 0x392ad6, 0x5e2ae0, 0x4892e0, 0x32cad5, 0x56c950, 0x40d4a0, 0x2bd4a3,
            0x50b690, 0x3a57a7, 0x6055b0, 0x4c25d0, 0x3695b5, 0x5a92b0, 0x44a950, 0x2ed954, 0x54b4a0, 0x3cb550,
            0x286b52, 0x4e55b0, 0x3a2776, 0x5e2570, 0x4852b0, 0x32aaa5, 0x56e950, 0x406aa0, 0x2abaa3, 0x50ab50
    }; /* Years 2000-2099 */

    public static final Integer[] TK20 = new Integer[]{
            0x3c4bd8, 0x624ae0, 0x4ca570, 0x3854d5, 0x5cd260, 0x44d950, 0x315554, 0x5656a0, 0x409ad0, 0x2a55d2,
            0x504ae0, 0x3aa5b6, 0x60a4d0, 0x48d250, 0x33d255, 0x58b540, 0x42d6a0, 0x2cada2, 0x5295b0, 0x3f4977,
            0x644970, 0x4ca4b0, 0x36b4b5, 0x5c6a50, 0x466d50, 0x312b54, 0x562b60, 0x409570, 0x2c52f2, 0x504970,
            0x3a6566, 0x5ed4a0, 0x48ea50, 0x336a95, 0x585ad0, 0x442b60, 0x2f86e3, 0x5292e0, 0x3dc8d7, 0x62c950,
            0x4cd4a0, 0x35d8a6, 0x5ab550, 0x4656a0, 0x31a5b4, 0x5625d0, 0x4092d0, 0x2ad2b2, 0x50a950, 0x38b557,
            0x5e6ca0, 0x48b550, 0x355355, 0x584da0, 0x42a5b0, 0x2f4573, 0x5452b0, 0x3ca9a8, 0x60e950, 0x4c6aa0,
            0x36aea6, 0x5aab50, 0x464b60, 0x30aae4, 0x56a570, 0x405260, 0x28f263, 0x4ed940, 0x38db47, 0x5cd6a0,
            0x4896d0, 0x344dd5, 0x5a4ad0, 0x42a4d0, 0x2cd4b4, 0x52b250, 0x3cd558, 0x60b540, 0x4ab5a0, 0x3755a6,
            0x5c95b0, 0x4649b0, 0x30a974, 0x56a4b0, 0x40aa50, 0x29aa52, 0x4e6d20, 0x39ad47, 0x5eab60, 0x489370,
            0x344af5, 0x5a4970, 0x4464b0, 0x2c74a3, 0x50ea50, 0x3d6a58, 0x6256a0, 0x4aaad0, 0x3696d5, 0x5c92e0
    }; /* Years 1900-1999 */

    public static final Integer[] TK21 = new Integer[]{
            0x46c960, 0x2ed954, 0x54d4a0, 0x3eda50, 0x2a7552, 0x4e56a0, 0x38a7a7, 0x5ea5d0, 0x4a92b0, 0x32aab5,
            0x58a950, 0x42b4a0, 0x2cbaa4, 0x50ad50, 0x3c55d9, 0x624ba0, 0x4ca5b0, 0x375176, 0x5c5270, 0x466930,
            0x307934, 0x546aa0, 0x3ead50, 0x2a5b52, 0x504b60, 0x38a6e6, 0x5ea4e0, 0x48d260, 0x32ea65, 0x56d520,
            0x40daa0, 0x2d56a3, 0x5256d0, 0x3c4afb, 0x6249d0, 0x4ca4d0, 0x37d0b6, 0x5ab250, 0x44b520, 0x2edd25,
            0x54b5a0, 0x3e55d0, 0x2a55b2, 0x5049b0, 0x3aa577, 0x5ea4b0, 0x48aa50, 0x33b255, 0x586d20, 0x40ad60,
            0x2d4b63, 0x525370, 0x3e49e8, 0x60c970, 0x4c54b0, 0x3768a6, 0x5ada50, 0x445aa0, 0x2fa6a4, 0x54aad0,
            0x4052e0, 0x28d2e3, 0x4ec950, 0x38d557, 0x5ed4a0, 0x46d950, 0x325d55, 0x5856a0, 0x42a6d0, 0x2c55d4,
            0x5252b0, 0x3ca9b8, 0x62a930, 0x4ab490, 0x34b6a6, 0x5aad50, 0x4655a0, 0x2eab64, 0x54a570, 0x4052b0,
            0x2ab173, 0x4e6930, 0x386b37, 0x5e6aa0, 0x48ad50, 0x332ad5, 0x582b60, 0x42a570, 0x2e52e4, 0x50d160,
            0x3ae958, 0x60d520, 0x4ada90, 0x355aa6, 0x5a56d0, 0x462ae0, 0x30a9d4, 0x54a2d0, 0x3ed150, 0x28e952
    }; /* Years 2000-2099 */

    public static final Integer[] TK22 = new Integer[]{
            0x4eb520, 0x38d727, 0x5eada0, 0x4a55b0, 0x362db5, 0x5a45b0, 0x44a2b0, 0x2eb2b4, 0x54a950, 0x3cb559,
            0x626b20, 0x4cad50, 0x385766, 0x5c5370, 0x484570, 0x326574, 0x5852b0, 0x406950, 0x2a7953, 0x505aa0,
            0x3baaa7, 0x5ea6d0, 0x4a4ae0, 0x35a2e5, 0x5aa550, 0x42d2a0, 0x2de2a4, 0x52d550, 0x3e5abb, 0x6256a0,
            0x4c96d0, 0x3949b6, 0x5e4ab0, 0x46a8d0, 0x30d4b5, 0x56b290, 0x40b550, 0x2a6d52, 0x504da0, 0x3b9567,
            0x609570, 0x4a49b0, 0x34a975, 0x5a64b0, 0x446a90, 0x2cba94, 0x526b50, 0x3e2b60, 0x28ab61, 0x4c9570,
            0x384ae6, 0x5cd160, 0x46e4a0, 0x2eed25, 0x54da90, 0x405b50, 0x2c36d3, 0x502ae0, 0x3a93d7, 0x6092d0,
            0x4ac950, 0x32d556, 0x58b4a0, 0x42b690, 0x2e5d94, 0x5255b0, 0x3e25fa, 0x6425b0, 0x4e92b0, 0x36aab6,
            0x5c6950, 0x4674a0, 0x31b2a5, 0x54ad50, 0x4055a0, 0x2aab73, 0x522570, 0x3a5377, 0x6052b0, 0x4a6950,
            0x346d56, 0x585aa0, 0x42ab50, 0x2e56d4, 0x544ae0, 0x3ca570, 0x2864d2, 0x4cd260, 0x36eaa6, 0x5ad550,
            0x465aa0, 0x30ada5, 0x5695d0, 0x404ad0, 0x2aa9b3, 0x50a4d0, 0x3ad2b7, 0x5eb250, 0x48b540, 0x33d556
    }; /* Years 2100-2199 */

    public static final double PI = Math.PI;
    public static final String[] CAN = {"Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỉ", "Canh", "Tân", "Nhâm", "Quý"};
    public static final String[] CHI = {"Tý", "Sửu", "Dần", "Mão", "Thìn", "Tị", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi"};
    public static final String[] TUAN = {"Thứ Hai", " Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy", "Chủ Nhật"};
    public static final String[] GIO_HD = {"110100101100", "001101001011", "110011010010", "101100110100", "001011001101", "010010110011"};
    public static final String[] GIO_AL = {"Tý (23h - 1h)", "Sửu (1h - 3h)", "Dần (3h - 5h)", "Mão (5h - 7h)", "Thìn (7h - 9h)", "Tị (9h - 11h)", "Ngọ (11h - 13h)", "Mùi (13h - 15h)",
                                            "Thân (15h - 17h)", "Dậu (17h - 19h)", "Tuất (19h - 21h)", "Hợi (21h - 23h)"};
    public static final String[] NGAY = {"Thứ Hai", " Thứ Ba", "Thứ Tư", "Thứ Năm", "Thứ Sáu", "Thứ Bảy", "Chủ Nhật"};
    public static final String[] TIET_KHI = {"Xuân Phân", "Thanh Minh", "Cốc Vũ", "Lập Hạ", "Tiểu Mãn", "Mang Chủng",
                                                "Hạ Chí", "Tiểu Thử", "Đại Thử", "Lập Thu", "Xử Thử", "Bạch Lộ",
                                                "Thu Phân", "Hàn Lộ", "Sương Giáng", "Lập Đông", "Tiểu Tuyết", "Đại Tuyết",
                                                "Đông Chí", "Tiểu Hàn", "Đại Hàn", "Lập Xuân", "Vũ Thủy", "Kinh Trập"};

    public static final int FIRST_DAY = jdn(25, 1, 1800); // Tet am lich 1800
    public static final int LAST_DAY = jdn(31, 12, 2199);

    public static int INT(double d) {
        return (int) Math.floor(d);
    }

    public static int jdn(int dd, int mm, int yy) {
        int a = INT((14 - mm) / 12);
        int y = yy + 4800 - a;
        int m = mm + 12 * a - 3;
        int jd = dd + INT((153 * m + 2) / 5) + 365 * y + INT(y / 4) - INT(y / 100) + INT(y / 400) - 32045;
        return jd;
    }

    public static Integer[] jdn2date(int jd) {
        int Z, A, alpha, B, C, D, E, dd, mm, yyyy, F;
        Z = jd;
        if (Z < 2299161) {
            A = Z;
        } else {
            alpha = INT((Z - 1867216.25) / 36524.25);
            A = Z + 1 + alpha - INT(alpha / 4);
        }
        B = A + 1524;
        C = INT((B - 122.1) / 365.25);
        D = INT(365.25 * C);
        E = INT((B - D) / 30.6001);
        dd = INT(B - D - INT(30.6001 * E));
        if (E < 14) {
            mm = E - 1;
        } else {
            mm = E - 13;
        }
        if (mm < 3) {
            yyyy = C - 4715;
        } else {
            yyyy = C - 4716;
        }
        return new Integer[]{dd, mm, yyyy};
    }

    public static ArrayList<LunarDate> decodeLunarYear(int yy, int k) {
        Integer[] monthLengths;
        Integer[] regularMonths;
        int offsetOfTet;
        int leapMonth;
        int leapMonthLength;
        int solarNY;
        int currentJD;
        int j;
        int mm;

        ArrayList<LunarDate> ly = new ArrayList<>();
        monthLengths = new Integer[]{29, 30};
        regularMonths = new Integer[12];
        offsetOfTet = k >> 17;
        leapMonth = k & 0xf;
        leapMonthLength = monthLengths[k >> 16 & 0x1];
        solarNY = jdn(1, 1, yy);
        currentJD = solarNY + offsetOfTet;
        j = k >> 4;
        for (int i = 0; i < 12; i++) {
            regularMonths[12 - i - 1] = monthLengths[j & 0x1];
            j >>= 1;
        }
        if (leapMonth == 0) {
            for (mm = 1; mm <= 12; mm++) {
                ly.add(new LunarDate(1, mm, yy, 0, currentJD, regularMonths[mm-1]));
                currentJD += regularMonths[mm - 1];
            }
        } else {
            for (mm = 1; mm <= leapMonth; mm++) {
                ly.add(new LunarDate(1, mm, yy, 0, currentJD, regularMonths[mm-1]));
                currentJD += regularMonths[mm - 1];
            }
            ly.add(new LunarDate(1, leapMonth, yy, 1, currentJD, regularMonths[mm-1]));
            currentJD += leapMonthLength;
            for (mm = leapMonth + 1; mm <= 12; mm++) {
                ly.add(new LunarDate(1, mm, yy, 0, currentJD, regularMonths[mm-1]));
                currentJD += regularMonths[mm - 1];
            }
        }
        return ly;
    }

    public static ArrayList<LunarDate> getYearInfo(int yyyy) {
        int yearCode;
        if (yyyy < 1900) {
            yearCode = TK19[yyyy - 1800];
        } else if (yyyy < 2000) {
            yearCode = TK20[yyyy - 1900];
        } else if (yyyy < 2100) {
            yearCode = TK21[yyyy - 2000];
        } else {
            yearCode = TK22[yyyy - 2100];
        }
        return decodeLunarYear(yyyy, yearCode);
    }

    public static LunarDate findLunarDate(int jd, ArrayList<LunarDate> ly) {
        if (jd > LAST_DAY || jd < FIRST_DAY || ly.get(0).getJd() > jd) {
            return new LunarDate(0, 0, 0, 0, jd, 0);
        }
        int i = ly.size() - 1;
        while (jd < ly.get(i).getJd()) {
            i--;
        }
        int off = jd - ly.get(i).getJd();
        LunarDate ret = new LunarDate(ly.get(i).getDay() + off, ly.get(i).getMonth(), ly.get(i).getYear(), ly.get(i).getLeap(), jd, ly.get(i).getNumday());
        return ret;
    }

    public static LunarDate getLunarDate(int dd, int mm, int yyyy) {
        ArrayList<LunarDate> ly;
        int jd;
        if (yyyy < 1800 || 2199 < yyyy) {
            //return new LunarDate(0, 0, 0, 0, 0);
        }
        ly = getYearInfo(yyyy);
        jd = jdn(dd, mm, yyyy);
        if (jd < ly.get(0).getJd()) {
            ly = getYearInfo(yyyy - 1);
        }
        return findLunarDate(jd, ly);
    }

    /* Compute the longitude of the sun at any time.
     * Parameter: floating number jdn, the number of days since 1/1/4713 BC noon
     * Algorithm from: "Astronomical Algorithms" by Jean Meeus, 1998
     */
    public static double SunLongitude(double jdn) {
        double T, T2, dr, M, L0, DL, lambda, theta, omega;
        T = (jdn - 2451545.0) / 36525; // Time in Julian centuries from 2000-01-01 12:00:00 GMT
        T2 = T * T;
        dr = PI / 180; // degree to radian
        M = 357.52910 + 35999.05030 * T - 0.0001559 * T2 - 0.00000048 * T * T2; // mean anomaly, degree
        L0 = 280.46645 + 36000.76983 * T + 0.0003032 * T2; // mean longitude, degree
        DL = (1.914600 - 0.004817 * T - 0.000014 * T2) * Math.sin(dr * M);
        DL = DL + (0.019993 - 0.000101 * T) * Math.sin(dr * 2 * M) + 0.000290 * Math.sin(dr * 3 * M);
        theta = L0 + DL; // true longitude, degree
        // obtain apparent longitude by correcting for nutation and aberration
        omega = 125.04 - 1934.136 * T;
        lambda = theta - 0.00569 - 0.00478 * Math.sin(omega * dr);
        // Convert to radians
        lambda = lambda * dr;
        lambda = lambda - PI * 2 * (INT(lambda / (PI * 2))); // Normalize to (0, 2*PI)
        return lambda;
    }

    public static double SunLongitude2(double jd1, int h, int mi, int s, int ms, double tmz) {
        //DateTime ts = dt.ToUniversalTime();
        //// GMT Julian date number
        double jd = jd1 + (double) h / 24 - 0.5
                + (double) mi / 1440 + (double) s / 86400 + (double) ms / 86400000 - tmz/24;

        //// Time in Julian centuries from 2000-01-01 12:00:00 GMT
        double t = (jd - 2451545.0) / 36525;

        double t2 = t * t;
        double dr = Math.PI / 180;  // Degree to radian

        //// Mean anomaly, degree
        double m = 357.52911D + 35999.05029 * t - 0.0001537 * t2
                - 0.00000048 * t * t2;

        //// Mean longitude, degree
        double l0 = 280.46646D + 36000.76983 * t + 0.0003032 * t2;

        double dl = (1.914602D - 0.004817 * t - 0.000014 * t2) * Math.sin(dr * m)
                + (0.019993D - 0.000101 * t) * Math.sin(dr * 2 * m)
                + 0.000289D * Math.sin(dr * 3 * m);

        //// True longitude, degree
        double l = l0 + dl;

        //// Obtain apparent longitude by correcting for nutation and aberration
        double omega = 125.04D - 1934.136 * t;
        l -= 0.00569D + 0.00478 * Math.sin(omega * dr);

        //// Normalize to (0, 2*PI)
        l -= (int) Math.floor(l / 360) * 360;

        //lambda *= dr; // Convert to radians
        return l;
    }

    /* Compute the sun segment at start (00:00) of the day with the given integral Julian day number.
     * The time zone if the time difference between local time and UTC: 7.0 for UTC+7:00.
     * The function returns a number between 0 and 23.
     * From the day after March equinox and the 1st major term after March equinox, 0 is returned.
     * After that, return 1, 2, 3 ...
     */
    public static int getSunLongitude(double dayNumber, double timeZone) {
        //return INT(SunLongitude(dayNumber/* - 0.5 - timeZone/24.0*/) / PI * 12);
        return INT(SunLongitude(dayNumber));
    }

    public static String getDayName(LunarDate lunarDate) {
        if (lunarDate.getDay() == 0) {
            return "";
        }
        String[] cc = getCanChi(lunarDate);
        String s = "Ngày " + cc[0] + ", tháng " + cc[1] + ", năm " + cc[2];
        return s;
    }

    public static String getYearCanChi(int year) {
        return CAN[(year + 6) % 10] + " " + CHI[(year + 8) % 12];
    }

    /*
     * Can cua gio Chinh Ty (00:00) cua ngay voi JDN nay
     */
    public static String getCanHour0(int jdn) {
        return CAN[(jdn - 1) * 2 % 10];

    }

    public static String[] getCanChi(LunarDate lunar) {
        String dayName, monthName, yearName;
        dayName = CAN[(lunar.getJd() + 9) % 10] + " " + CHI[(lunar.getJd() + 1) % 12];
        monthName = CAN[(lunar.getYear() * 12 + lunar.getMonth() + 3) % 10] + " " + CHI[(lunar.getMonth() + 1) % 12];
        if (lunar.getLeap() == 1) {
            monthName += " (Nhuận)";
        }
        yearName = getYearCanChi(lunar.getYear());
        return new String[]{dayName, monthName, yearName};
    }

    public static String getDayString(LunarDate lunar, int solarDay, int solarMonth, int solarYear) {
        String s;
        String dayOfWeek = TUAN[(lunar.getJd() + 1) % 7];
        s = dayOfWeek + " " + solarDay + "/" + solarMonth + "/" + solarYear;
        s += " -+- ";
        s = s + "Ngày " + lunar.getDay() + " tháng " + lunar.getMonth();
        if (lunar.getLeap() == 1) {
            s = s + " nhuận";
        }
        return s;
    }

    public static String getGioHoangDao(int jd) {
        int chiOfDay = (jd + 1) % 12;
        String gioHD = GIO_HD[chiOfDay % 6]; // same values for Ty' (1) and Ngo. (6), for Suu and Mui etc.
        String ret = "";
        int count = 0;
        for (int i = 0; i < 12; i++) {
            if (gioHD.charAt(i) == '1') {
                ret += GIO_AL[i] + " ";
                //ret += '('+(i*2+23)%24+'-'+(i*2+1)%24+')';
                //if (count++ < 5) ret += ",";
                //if (count == 3) ret += '\n';
            }
        }
        return ret;
    }

    public static String convertToLunarTime(double h, int jd) {
        int canIdxH;
        int chiIdxH;
        int canIdxNN = (jd + 9) % 10;

        if (h >= 1 && h < 3) {
            chiIdxH = 1;
            if (canIdxNN == 0 || canIdxNN == 5) {
                canIdxH = 1;
            } else if (canIdxNN == 1 || canIdxNN == 6) {
                canIdxH = 3;
            } else if (canIdxNN == 2 || canIdxNN == 7) {
                canIdxH = 5;
            } else if (canIdxNN == 3 || canIdxNN == 8) {
                canIdxH = 7;
            } else {
                canIdxH = 9;
            }
        } else if (h >= 3 && h < 5) {
            chiIdxH = 2;
            if (canIdxNN == 0 || canIdxNN == 5) {
                canIdxH = 2;
            } else if (canIdxNN == 1 || canIdxNN == 6) {
                canIdxH = 4;
            } else if (canIdxNN == 2 || canIdxNN == 7) {
                canIdxH = 6;
            } else if (canIdxNN == 3 || canIdxNN == 8) {
                canIdxH = 8;
            } else {
                canIdxH = 0;
            }
        } else if (h >= 5 && h < 7) {
            chiIdxH = 3;
            if (canIdxNN == 0 || canIdxNN == 5) {
                canIdxH = 3;
            } else if (canIdxNN == 1 || canIdxNN == 6) {
                canIdxH = 5;
            } else if (canIdxNN == 2 || canIdxNN == 7) {
                canIdxH = 7;
            } else if (canIdxNN == 3 || canIdxNN == 8) {
                canIdxH = 9;
            } else {
                canIdxH = 1;
            }
        } else if (h >= 7 && h < 9) {
            chiIdxH = 4;
            if (canIdxNN == 0 || canIdxNN == 5) {
                canIdxH = 4;
            } else if (canIdxNN == 1 || canIdxNN == 6) {
                canIdxH = 6;
            } else if (canIdxNN == 2 || canIdxNN == 7) {
                canIdxH = 8;
            } else if (canIdxNN == 3 || canIdxNN == 8) {
                // note
                canIdxH = 0;
            } else {
                canIdxH = 2;
            }
        } else if (h >= 9 && h < 11) {
            chiIdxH = 5;
            if (canIdxNN == 0 || canIdxNN == 5) {
                canIdxH = 5;
            } else if (canIdxNN == 1 || canIdxNN == 6) {
                canIdxH = 7;
            } else if (canIdxNN == 2 || canIdxNN == 7) {
                canIdxH = 9;
            } else if (canIdxNN == 3 || canIdxNN == 8) {
                canIdxH = 1;
            } else {
                canIdxH = 3;
            }
        } else if (h >= 11 && h < 13) {
            chiIdxH = 6;
            if (canIdxNN == 0 || canIdxNN == 5) {
                canIdxH = 6;
            } else if (canIdxNN == 1 || canIdxNN == 6) {
                canIdxH = 8;
            } else if (canIdxNN == 2 || canIdxNN == 7) {
                canIdxH = 0;
            } else if (canIdxNN == 3 || canIdxNN == 8) {
                canIdxH = 2;
            } else {
                canIdxH = 4;
            }
        } else if (h >= 13 && h < 15) {
            chiIdxH = 7;
            if (canIdxNN == 0 || canIdxNN == 5) {
                canIdxH = 7;
            } else if (canIdxNN == 1 || canIdxNN == 6) {
                canIdxH = 9;
            } else if (canIdxNN == 2 || canIdxNN == 7) {
                canIdxH = 1;
            } else if (canIdxNN == 3 || canIdxNN == 8) {
                canIdxH = 3;
            } else {
                canIdxH = 5;
            }
        } else if (h >= 15 && h < 17) {
            chiIdxH = 8;
            if (canIdxNN == 0 || canIdxNN == 5) {
                canIdxH = 8;
            } else if (canIdxNN == 1 || canIdxNN == 6) {
                canIdxH = 0;
            } else if (canIdxNN == 2 || canIdxNN == 7) {
                canIdxH = 2;
            } else if (canIdxNN == 3 || canIdxNN == 8) {
                canIdxH = 4;
            } else {
                canIdxH = 6;
            }
        } else if (h >= 17 && h < 19) {
            chiIdxH = 9;
            if (canIdxNN == 0 || canIdxNN == 5) {
                canIdxH = 9;
            } else if (canIdxNN == 1 || canIdxNN == 6) {
                canIdxH = 1;
            } else if (canIdxNN == 2 || canIdxNN == 7) {
                canIdxH = 3;
            } else if (canIdxNN == 3 || canIdxNN == 8) {
                canIdxH = 5;
            } else {
                canIdxH = 7;
            }
        } else if (h >= 19 && h < 21) {
            chiIdxH = 10;
            if (canIdxNN == 0 || canIdxNN == 5) {
                canIdxH = 0;
            } else if (canIdxNN == 1 || canIdxNN == 6) {
                canIdxH = 2;
            } else if (canIdxNN == 2 || canIdxNN == 7) {
                canIdxH = 4;
            } else if (canIdxNN == 3 || canIdxNN == 8) {
                canIdxH = 6;
            } else {
                canIdxH = 8;
            }
        } else if (h >= 21 && h < 23) {
            chiIdxH = 11;
            if (canIdxNN == 0 || canIdxNN == 5) {
                canIdxH = 1;
            } else if (canIdxNN == 1 || canIdxNN == 6) {
                canIdxH = 3;
            } else if (canIdxNN == 2 || canIdxNN == 7) {
                canIdxH = 5;
            } else if (canIdxNN == 3 || canIdxNN == 8) {
                canIdxH = 7;
            } else {
                canIdxH = 9;
            }
        } else {
            chiIdxH = 0;
            if (canIdxNN == 0 || canIdxNN == 5) {
                canIdxH = 0;
            } else if (canIdxNN == 1 || canIdxNN == 6) {
                canIdxH = 2;
            } else if (canIdxNN == 2 || canIdxNN == 7) {
                canIdxH = 4;
            } else if (canIdxNN == 3 || canIdxNN == 8) {
                canIdxH = 6;
            } else {
                canIdxH = 8;
            }
        }
        return CAN[canIdxH] + " " + CHI[chiIdxH];
    }

    public static String getTietKhi(double jd, int h, int m, int s, int ms, double timezone) {
        double sl = SunLongitude2(jd, h, m, s, ms, timezone);
        //// TODO: Hard-code some exceptions
        if (sl > 283.980685381734 && sl < 283.998429466957) sl += 1.019314618264603;
        else if (sl > 268.982233589428 && sl < 268.997418971464) sl += 1.017766410571303;
        else if (sl > 238.994360790922 && sl < 238.998815336006) sl += 1.0056392090773516;
        else if (sl > 298.985299986357 && sl < 298.999892901616) sl += 1.014700013642128;
        else if (sl > 313.988180094718 && sl < 313.995561330842) sl += 1.011819905281312;
        else if (sl > 253.986425624963 && sl < 253.997690579273) sl += 1.0135743750361216;
            //// Not needle
        else if (sl > 328.99464285669 && sl < 328.997513050222) sl += 1.0053571433090626;
            //else if (Math.Round(sl, 10) == 343.9994746162) { }
        else if (sl > 343.998197182546 && sl < 343.999951809195) sl += 1.0018028174527066;
            //// ---
        else if ((sl > 194.002942346778 && sl < 194.013760798233)   // && Math.Round(sl, 10) != 194.0128520093)
                || (sl > 179.000208750401 && sl < 179.01862004219)
                || (sl > 164.000901415201 && sl < 164.027376866403)
                || (sl > 149.000992194126 && sl < 149.035876318057)
                || (sl > 134.000122471553 && sl < 134.038649430489)
                || (sl > 119.00248079265 && sl < 119.035483698587)
                || (sl > 104.001461964773 && sl < 104.046198136007)
                || (sl > 89.0013457619861 && sl < 89.0445839193854)
                || (sl > 74.000266017334 && sl < 74.0402776596676)
                || (sl > 59.0034474952262 && sl < 59.034047233342)
                || (sl > 44.0005194120248 && sl < 44.026037863635)
                || (sl > 29.0003734693891 && sl < 29.0216673913857)
                || (sl > 14.0025458727477 && sl < 14.015670561109)      // && Math.Round(sl, 10) != 14.0151613926)
                || (sl > 359.002244547314 && sl < 359.005330829566)
                || Math.round(sl) == 209.00033593538) { }   // ? 209.000335935378 ?
        else sl += 1.0;

        // 24 / 360 == 1 / 15
        int i = (int)Math.floor(sl / 15);
        return TIET_KHI[i % 24];
    }
}
