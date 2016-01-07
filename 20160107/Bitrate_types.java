package session;

import java.util.*;
import java.io.*;
import java.util.regex.*;

public class Bitrate_types {
	static HashSet<String> chunks = new HashSet<String>();
	static HashSet<String> logs = new HashSet<String>();
	static ArrayList<String> bitrate_switching = new ArrayList<String>();
	static HashMap<String, String[]> video_info = new HashMap<String, String[]>();
	static HashMap<String, String> client_device = new HashMap<String, String>();

	public static void main(String[] args) {
		String root_dir = "/mnt/2t/Bestv_cdn/";
		File log_format = new File(root_dir + "session_format");
		File[] server_logs = log_format.listFiles();
		sort(server_logs);
		
		String s="";
		String key_back = "";
		int size_back = 0;
		String chunk_back = "";
		int bitrate_gap = 0;
		chunks.clear();
		logs.clear();
		bitrate_switching.clear();
		video_info.clear();
		client_device.clear();
		try {
			{
				FileInputStream is = new FileInputStream(root_dir + "info/session_video");
				BufferedReader in  = new BufferedReader(new InputStreamReader(is, "utf-8"));
				while ((s=in.readLine())!=null){
					String ss[] = s.split("\t");
					if (!ss[4].equals("") && !ss[4].equals("0")) {
						String values[] = new String[3];
						values[0] = ss[2];
						values[1] = ss[3];
						values[2] = ss[4];
						video_info.put(ss[0], values);
					}
				}
				in.close();
				
				is = new FileInputStream(root_dir + "info/session_video_fix");
				in  = new BufferedReader(new InputStreamReader(is, "utf-8"));
				while ((s=in.readLine())!=null){
					String ss[] = s.split("\t");
					String values[] = new String[3];
					values[0] = ss[1];
					values[1] = ss[2];
					values[2] = ss[3];
					video_info.put(ss[0], values);
				}
				in.close();
				
				is = new FileInputStream(root_dir + "client_device");
				in  = new BufferedReader(new InputStreamReader(is, "utf-8"));
				while ((s=in.readLine())!=null){
					String ss[] = s.split("\t");
					client_device.put(ss[0], ss[1]);
				}
				in.close();
			}
			
			String type = "";
			int num = 0;
			int bitrate_back = 0;
			int bitrate_from = 0, bitrate_to = 0, bitrate_from_back = 0;
			boolean firstchunk = true;
			String firstchunktime = "";
			String firstchunksize = "";
			String starttime = "";
			String timestamp = "";
			String timestamp_back = "";
			FileOutputStream os = new FileOutputStream(root_dir + "session/bitrate_types/summary");
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(os, "utf-8"));
			for (int i=0; i<server_logs.length; i++) {
			//for (int i=0; i<1; i++) {
				System.out.println(server_logs[i].getName());
				
				FileInputStream is = new FileInputStream(server_logs[i]);
				BufferedReader in  = new BufferedReader(new InputStreamReader(is, "utf-8"));
				while ((s=in.readLine())!=null) {
					String[] ss = s.split("\t");
					String key = server_logs[i].getName() + "\t" + ss[0] + "\t" + ss[1] + "\t" + ss[2] + "\t" + ss[3] + "\t" + ss[4] + "\t" + ss[12] + "\t" + get_device(ss[12]);
					String chunk = ss[9];
					int bitrate = 0;
					try {
						bitrate = Integer.parseInt(ss[8]);
					} catch (Exception e) {
						System.out.println(s);
						continue;
					}
					if (key.equals(key_back)) {
						logs.add(s);
						if (chunk.endsWith(".ts")) {
							if (firstchunk) {
								firstchunktime = ss[6];
								firstchunksize = ss[11];
								firstchunk = false;
							}
							chunks.add(check_chunk(chunk));
							if (bitrate!=bitrate_back) {
								if (bitrate_back!=0) {
									if (!type.equals("")) {
										//add bitrate type to list
										if (bitrate>bitrate_back) {
											bitrate_switching.add(num + "\t" + type + "\tup\t" + size_back + "\t" + timestamp_back + "\t" + chunk_back + "\t" +(chunks.size()-size_back) + "\t" + bitrate_gap + "\t" + bitrate_from_back + "\t" + bitrate_from + "\t" + bitrate_to + "\t" + bitrate + "\ting");
										} else {
											bitrate_switching.add(num + "\t" + type + "\tdown\t" + size_back + "\t" + timestamp_back + "\t" + chunk_back + "\t" +(chunks.size()-size_back) + "\t" + bitrate_gap + "\t" + bitrate_from_back + "\t" + bitrate_from + "\t" + bitrate_to + "\t" + bitrate + "\ting");
										}
									}
									bitrate_gap = bitrate-bitrate_back;
									if (bitrate_gap>0) {
										if (type.equals("")) {
											type = "up";
										} else if (type.equals("up") || type.equals("down-up") || type.equals("up-up")) {
											type = "up-up";
										} else if (type.equals("down") || type.equals("up-down") || type.equals("down-down")) {
											type = "down-up";
										}
									} else {
										if (type.equals("")) {
											type = "down";
										} else if (type.equals("up") || type.equals("down-up") || type.equals("up-up")) {
											type = "up-down";
										} else if (type.equals("down") || type.equals("up-down") || type.equals("down-down")) {
											type = "down-down";
										}
									}
									num++;
									bitrate_from_back = bitrate_from;
									bitrate_from = bitrate_back;
									bitrate_to = bitrate;
								}
								bitrate_back = bitrate;
								size_back = chunks.size();
								chunk_back = chunk;
								timestamp_back = ss[7];
							}
						}
						timestamp = ss[7];
					} else {
						if (chunks.size()!=0) {
							//cout session summary 
							String ss_back[] = key_back.split("\t");
							if (video_info.containsKey(ss_back[5])) {
								if (type.equals("")) {
									type = "constant";
									bitrate_switching.add(num + "\t" + type + "\tend\t" + size_back + "\t" + timestamp_back + "\t" + chunk_back + "\t" +(chunks.size()-size_back) + "\t" + bitrate_gap + "\t" + bitrate_from_back + "\t" + bitrate_back + "\t" + bitrate_back + "\t0\tlast");
								} else {
									bitrate_switching.add(num + "\t" + type + "\tend\t" + size_back + "\t" + timestamp_back + "\t" + chunk_back + "\t" +(chunks.size()-size_back) + "\t" + bitrate_gap + "\t" + bitrate_from_back + "\t" + bitrate_from + "\t" + bitrate_to + "\t0\tlast");
								}
								String info[] = video_info.get(ss_back[5]);
								int play_percent = 1000*chunks.size()/Integer.parseInt(info[2]);
								String is_over = play_percent>80?"yes":"no";
								for (String va : bitrate_switching) {
									String tt[] = va.split("\t");
									out.write(key_back + "\t" + starttime + "\t" + timestamp + "\t" + info[0] + "\t" + info[1] + "\t" + info[2] + "\t" + chunks.size() + "\t" + play_percent + "\t" + is_over + "\t" + tt[0] + "\t" + tt[1] + "\t" + tt[2] + "\t" + tt[3] + "\t" + (1000*Integer.parseInt(tt[3])/Integer.parseInt(info[2])) + "\t" + tt[4] + "\t" + tt[5] + "\t" + tt[6] + "\t" + tt[7] + "\t" + tt[8] + "\t" + tt[9] + "\t" + tt[10] + "\t" + tt[11] + "\t" + tt[12] + "\t" + firstchunktime + "\t" + firstchunksize + "\n");
									out.flush();
								}
							}
						}
						chunks.clear();
						bitrate_switching.clear();
						logs.clear();
						logs.add(s);
						type = "";
						key_back = key;
						bitrate_back = 0;
						bitrate_from = 0;
						bitrate_from_back = 0;
						bitrate_to = 0;
						chunk_back = "";
						size_back = 0;
						num = 0;
						starttime = ss[7];
						timestamp = ss[7];
						timestamp_back = "";
						bitrate_gap = 0;
						firstchunk = true;
					}
				}
				in.close();
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void sort(File[] files) {
		for (int i=0; i<files.length; i++) {
			for (int j=0; j<files.length; j++) {
				if (files[i].compareTo(files[j])<0) {
					File tmp = files[i];
					files[i] = files[j];
					files[j] = tmp;
				}
			}
		}
	}
	
	static String get_device(String client) {
		if (client_device.containsKey(client)) {
			return client_device.get(client);
		}
		return "other";
	}
	
	static Pattern pat1 = Pattern.compile("mp4\\.(\\d+)\\.to\\.ts");
	static Pattern pat2 = Pattern.compile("(\\d+)\\.ts");
	static Pattern pat3 = Pattern.compile("_(.*?)\\.");
	static String check_chunk(String chunk) {
		if (chunk.contains("mp4")) {
			chunk = chunk.replaceAll(",", "");
			String num = "";
			if (chunk.contains("_")) {
				Matcher mat = pat3.matcher(chunk);
				if (mat.find()) {
					num = mat.group(1);
				}
			}
			Matcher mat = pat1.matcher(chunk);
			if (mat.find()) {
				try {
					return num + "_" + mat.group(1);
				} catch (Exception e) {
					System.out.println(chunk);
					return "-1";
				}
			} else {
				System.out.println(chunk);
				return "-1";
			}
		} else {
			Matcher mat = pat2.matcher(chunk);
			if (mat.find()) {
				try {
					return mat.group(1);
				} catch (Exception e) {
					System.out.println(chunk);
					return "-1";
				}
			} else {
				System.out.println(chunk);
				return "-1";
			}
		}
	}
}
