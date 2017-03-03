public static final String URL = "http://fund.eastmoney.com/data/rankhandler.aspx?op=ph&dt=kf&ft=%1$s&rs=&gs=0&sc=3nzf&st=desc&sd=2012-02-03&ed=2017-03-03&qdii=&tabSubtype=,,,,,&pi=%2$s&pn=50&dx=1&v=0.8106373774466165";

    private static final String[] TYPES = {"hh", "gp", "zq", "zs", "bb", "qdii", "lof"};
    private static final String[] NAMES = {"混合型", "股票型", "债券型", "指数型", "保本型", "GDII型", "LOF型"};

	

	///////////////////////////////////////////////////////////////////////////////
	// 抓取数据段
	///////////////////////////////////////////////////////////////////////////////
    private void getData(final int index, final int page)
    {
        if (index >= TYPES.length) return;

        String url = String.format(URL, TYPES[index], page);
        OkHttpClient client = new OkHttpClient.Builder().build();
        final Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String data = response.body().string();
                if (!TextUtils.isEmpty(data))
                {
                    data = data.substring(data.indexOf('['));
                    data = data.substring(0, data.lastIndexOf(']') + 1);

                    if (data.length() > 2)
                    {
                        write(data, TYPES[index] + "-" + page);
                        getData(index, page + 1);
                        Log.d(TAG, "onResponse: 拿到数据 " + NAMES[index] + page);
                    } else
                    {
                        Log.w(TAG, "onResponse: 没有数据 " + NAMES[index] + page + "; 进行下一项");
                        getData(index + 1, 1);
                    }
                }
            }
        });
    }

    private void write(String content, String filename)
    {
        if (!TextUtils.isEmpty(content))
        {
            File file = new File(getExternalCacheDir(), filename + ".json");
            Log.e(TAG, "write: " + file.getAbsolutePath());
            try
            {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(content);
                writer.flush();
                writer.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
	
	
	
	

public class Parser {
	
	public static final String[] TYPES = {"hh", "gp", "zq", "zs", "bb", "qdii", "lof"};
    public static final String[] NAMES = {"混合型", "股票型", "债券型", "指数型", "保本型", "GDII型", "LOF型"};
	
	public static void main(String[] args) {
		
		Parser parser = new Parser();
		
		parser.op();
	}
	
	private void op()
	{
		Gson gson = new Gson();
		for (int i = 0; i < NAMES.length; i++)
		{
			List<Fund> funds = new ArrayList<>();
			File[] files = listFileByName(NAMES[i]);
			for (File f : files)
			{
				funds.addAll(parse(read(f)));
			}
			String content = gson.toJson(funds);
			System.out.println(content);
			write(content, NAMES[i]);
		}
	}

	private File[] listFileByName(String name)
	{
		return new File("json").listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().contains(name);
			}
		});
	}
	
	private List<Fund> parse(String content)
	{
		List<Fund> list = new ArrayList<>();
		
		
		try
		{

			JSONArray array = new JSONArray(content);
			int length = array.length();
			
			for (int i = 0; i < length; i++)
			{
				String item = array.optString(i);
				String[] strs = item.split(",");
				
				Fund fund = new Fund();
				fund.setCode(strs[0]);
				fund.setName(strs[1]);
				fund.setDanweijingzhi(toFloat(strs[4]));
				fund.setLeijijingzhi(toFloat(strs[5]));
				fund.setDay(toFloat(strs[6]));
				fund.setWeek(toFloat(strs[7]));
				fund.setMonth(toFloat(strs[8]));
				fund.setMonth3(toFloat(strs[9]));
				fund.setHalfyear(toFloat(strs[10]));
				fund.setYear(toFloat(strs[11]));
				fund.setYear2(toFloat(strs[12]));
				fund.setYear3(toFloat(strs[13]));
				fund.setThisyear(toFloat(strs[14]));
				fund.setFounded(toFloat(strs[15]));
				fund.setYear5(toFloat(strs[18]));
				
				System.out.println(fund);
				list.add(fund);
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	
		
		return list;
	}
	
	private float toFloat(String s)
	{
		if (s == null || s.length() == 0)
		{
			return 0;
		}
		return Float.parseFloat(s);
	}
	
	private String read(File f)
	{
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null)
			{
				sb.append(line);
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private void write(String content, String filename)
	{
		try {
			FileOutputStream fos = new FileOutputStream(new File("output", filename + ".json"));
			fos.write(content.getBytes());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

	
	

public class Fund {

	// 序号 基金代码 基金简称 日期 单位净值 累计净值 日增长率 近1周 近1月 近3月 近6月 近1年 近2年 近3年 今年来 成立来 自定义
	// 手续费

	private String code;
	private String name;
	private float danweijingzhi;
	private float leijijingzhi;
	private float day;
	private float week;
	private float month;
	private float month3;
	private float halfyear;
	private float year;
	private float year2;
	private float year3;
	private float year5;
	private float thisyear;
	private float founded;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getDanweijingzhi() {
		return danweijingzhi;
	}

	public void setDanweijingzhi(float danweijingzhi) {
		this.danweijingzhi = danweijingzhi;
	}

	public float getLeijijingzhi() {
		return leijijingzhi;
	}

	public void setLeijijingzhi(float leijijingzhi) {
		this.leijijingzhi = leijijingzhi;
	}

	public float getDay() {
		return day;
	}

	public void setDay(float day) {
		this.day = day;
	}

	public float getWeek() {
		return week;
	}

	public void setWeek(float week) {
		this.week = week;
	}

	public float getMonth() {
		return month;
	}

	public void setMonth(float month) {
		this.month = month;
	}

	public float getMonth3() {
		return month3;
	}

	public void setMonth3(float month3) {
		this.month3 = month3;
	}

	public float getHalfyear() {
		return halfyear;
	}

	public void setHalfyear(float halfyear) {
		this.halfyear = halfyear;
	}

	public float getYear() {
		return year;
	}

	public void setYear(float year) {
		this.year = year;
	}

	public float getYear2() {
		return year2;
	}

	public void setYear2(float year2) {
		this.year2 = year2;
	}

	public float getYear3() {
		return year3;
	}

	public void setYear3(float year3) {
		this.year3 = year3;
	}

	public float getYear5() {
		return year5;
	}

	public void setYear5(float year5) {
		this.year5 = year5;
	}

	public float getThisyear() {
		return thisyear;
	}

	public void setThisyear(float thisyear) {
		this.thisyear = thisyear;
	}

	public float getFounded() {
		return founded;
	}

	public void setFounded(float founded) {
		this.founded = founded;
	}

	@Override
	public String toString() {
		return "Fund [code=" + code + ", name=" + name + ", danweijingzhi=" + danweijingzhi + ", leijijingzhi="
				+ leijijingzhi + ", day=" + day + ", week=" + week + ", month=" + month + ", month3=" + month3
				+ ", halfyear=" + halfyear + ", year=" + year + ", year2=" + year2 + ", year3=" + year3 + ", year5="
				+ year5 + ", thisyear=" + thisyear + ", founded=" + founded + "]";
	}
}
