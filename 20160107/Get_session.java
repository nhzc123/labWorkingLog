package session;
//get session with userid.

import java.io.*;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Get_session {
	static LinkedHashMap<String, ArrayList<String>> Sessions = new LinkedHashMap<String, ArrayList<String>>();
	static HashMap<String, Integer> device_id = new HashMap<String, Integer>();
	static SimpleDateFormat sdf = new SimpleDateFormat("[dd/MMMMM/yyyy:HH:mm:ss Z]", Locale.US);
	static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd HHmmss");
	static Pattern pat_video = Pattern.compile("GET /{1,2}\\d{1,2}/(\\d{3})/(\\d{3})/");//identify a video
	static Pattern pat_disk = Pattern.compile("/{1,2}(\\d{1,2})/");//identify a disk
	static Pattern pat_video_code = Pattern.compile("_code=(.*?)&");//get video code
	static Pattern pat_userid = Pattern.compile("userid=(.*?)&|userId=(.*?)&");
	static Pattern pat_bitrate = Pattern.compile("_bitrate=(.*?)&|_bitrate=(.*?) ");
	static Pattern pat_client = Pattern.compile("_client=(.*?)&");
	static Iterator<Map.Entry<String,  ArrayList<String>>> it;
	static Map.Entry<String,  ArrayList<String>> entry;
	static int startline = 0;
	static int endline = 0;
	static String cdn_server = "";
	static HashMap<String, Integer> s_bitrate = new HashMap<String, Integer>();
	static HashMap<String, Integer> st_bitrate = new HashMap<String, Integer>();
	static HashSet<String> chunks = new HashSet<String>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s="";
		String file = "";
		try{
			int begin = Integer.parseInt(args[0]);
			int end = Integer.parseInt(args[1]);

			String root_dir = "/mnt/1t/backup/2013Data/";
			//read device_id file
			/*
			{
				FileInputStream is = new FileInputStream(root_dir + "device/total_server");
				InputStreamReader ir = new InputStreamReader(is, "utf-8");
				BufferedReader in = new BufferedReader(ir);
				s = in.readLine();
				while ((s=in.readLine())!=null) {
					String ss[] = s.split("\t");	//id	device	num
					device_id.put(ss[1], Integer.parseInt(ss[0]));
				}
				in.close();
				ir.close();
				is.close();
			}
			*/
			s_bitrate.clear();
			st_bitrate.clear();
			st_bitrate.put("S1", 207);
			st_bitrate.put("S2", 382);
			st_bitrate.put("S3", 582);
			st_bitrate.put("S4", 932);
			st_bitrate.put("S5", 32);

			File log_dir = new File(root_dir + "10.49.8.11/");
			File[] files = log_dir.listFiles();	//cdn_servers
			sort(files);
			FileOutputStream os = new FileOutputStream(root_dir + "session/sessions" + begin + "_" + end);
			OutputStreamWriter or = new OutputStreamWriter(os, "utf-8");
			BufferedWriter out = new BufferedWriter(or);
			/*FileOutputStream osn = new FileOutputStream(root_dir + "session/not" + begin + "_" + end);
			OutputStreamWriter orn = new OutputStreamWriter(osn, "utf-8");
			BufferedWriter outn = new BufferedWriter(orn);*/
			//server userid key(ip video device) video_code diskid starttime endtime startline endline startinfile endinfile startinfile_f endinfor_f lognum s_num s1 s2 s3 s4 m3u8_num t_time t_size speed switch_num
			//out.write("cdn_server\tuserid\tip\tvideo\tdevice\tvideo_code\tstarttime\tendtime\tstartline\tendline\tstartinfile\tendinfile\tstartinfile_f\tendinfile_f\tlognum\ts_num\ts1\ts2\ts3\ts4\tm3u8_num\tt_time\tt_size\tspeed\tswitch_num\n");
			//out.flush();
			for (int i=begin; i<end; i++) {
				Sessions.clear();
				cdn_server = files[i].getName();
				System.out.println(i + "\t" + cdn_server + ":\t" + sdf.format(new Date()));
				startline = 0;
				endline = 0;
				RandomAccessFile rf = new RandomAccessFile(root_dir + "session_origin/" + cdn_server, "rw");	//session
				RandomAccessFile rf_format = new RandomAccessFile(root_dir + "session_format/" + cdn_server, "rw");	//format session
				//userid key(ip videoid device) video_code diskid time timestamp  Bitrate filename ret size
				//rf_format.writeBytes("userid\tip\tvideo\tdevice\tvideo_code\tdiskid\ttime\ttimestamp\tBitrate\tfilename\tret\tsize\n");
				File[] dir1 = files[i].listFiles();	//years
				sort(dir1);
				for (int j=0; j<dir1.length; j++) {
					File[] dir2 = dir1[j].listFiles(); //months
					sort(dir2);
					for (int k=0; k<dir2.length; k++) {
						File[] dir3 = dir2[k].listFiles();	//days
						sort(dir3);
						for (int p=0; p<dir3.length; p++) {
							long day = sdf2.parse(dir1[j].getName() + dir2[k].getName() + dir3[p].getName() + " 000000").getTime();

							//write session
							it = Sessions.entrySet().iterator();
							while (it.hasNext()) {
								write_session(out, rf, rf_format, day);
							}
							//read log
							File[] logs = dir3[p].listFiles(); //log
							if (logs.length>0) {
								file  = logs[0].getAbsolutePath();
								FileInputStream is = new FileInputStream(logs[0]);
								InputStreamReader ir = new InputStreamReader(is, "utf-8");
								BufferedReader in = new BufferedReader(ir);
								while ((s=in.readLine())!=null) {
									String key = get_key(s);
									if (key==null) {
										/*outn.write(s + "\n");
										outn.flush();*/
										continue;
									}
									if (Sessions.containsKey(key)) {
										/*long now = get_time(s);
										ArrayList<String> t_list = Sessions.get(key);
										long latest = get_time(t_list.get(t_list.size()-1));
										if ((now-latest > 3600000) && s.contains("userid=")) {
											write_session(out, rf, rf_format, sdf2.parse("20140101 000000").getTime());
										}*/

										Sessions.get(key).add(s);
									} else {
										if (s.contains("/000/index.m3u8") || s.contains("userId")) {
											ArrayList<String> t_list = new ArrayList<String>();
											t_list.add(s);
											Sessions.put(key, t_list);
										}
									}
								}
								in.close();
								ir.close();
								is.close();
							}
						}
					}
				}
				//write last day session
				{
					it = Sessions.entrySet().iterator();
					while (it.hasNext()) {
						write_session(out, rf, rf_format, sdf2.parse("20140101 000000").getTime());
					}
				}
				rf.close();
				rf_format.close();
			}
			out.close();
			or.close();
			os.close();
			/*outn.close();
			orn.close();
			osn.close();*/
		} catch(Exception e) {
			System.out.println(cdn_server);
			System.out.println(file);
			System.out.println(s);
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

	static long get_time(String log) throws ParseException {
		String ss[] = log.split(" ");
		return sdf.parse(ss[4] + " " + ss[5]).getTime();
	}

	static String get_key(String log) {
		String ss[] = log.split("\"");
		if (ss.length==8 || ss.length==9 || ss.length==10 || ss.length==12) {
			String ss0[] = ss[0].split(" ");
			if (ss0.length==6) {
				Matcher mat = pat_video.matcher(ss[1]);
				if (mat.find()) {
					return ss0[1] + "\t" + mat.group(1) + " " + mat.group(2) + "\t" + device_id.get(ss[5]);//ip videoid device
				}
			}
		} else {
			System.out.println(ss.length + "&&&&&&&&&&&&" + log);
		}
		return null;
	}

	//get userid through pat_userid
	static String get_userid(String log) {
		Matcher mat = pat_userid.matcher(log);
		if (mat.find()) {
			if (mat.group(1)==null) {
				return mat.group(2);
			} else {
				return mat.group(1);
			}
		}
		return "000000000000000";
	}

	//write session
	static void write_session(BufferedWriter out, RandomAccessFile rf, RandomAccessFile rf_format, long day) throws Exception {
		Map.Entry<String, ArrayList<String>> entry = it.next();
		ArrayList<String> t_list = entry.getValue();

		chunks.clear();
		long time_s = get_time(t_list.get(0));
		long time_e = get_time(t_list.get(t_list.size()-1));
		if ((day-time_s>86400000) && (day-time_e)>7200000) {
			String userid = get_userid(t_list.get(0));
			int lognum = t_list.size();
			endline = startline + lognum;
			int s_num=0, s1=0, s2=0, s3=0, s4=0, s5=0, s6=0, s7=0, m3u8_num=0, t_size=0, switch_num=0;
			double t_time=0.0, speed=0.0;
			long startinfile = rf.getFilePointer();
			long startinfile_f = rf_format.getFilePointer();
			String Bitrate = "";
			String video_code = " ";
			String diskid = "";
			String keys[] = entry.getKey().split("\t");
			String video_path = keys[1];
			String client = "-10";
			for (int q=0; q<t_list.size(); q++) {
				String line = t_list.get(q);
				String ss[] = line.split("\"");
				ss[1] = URLDecoder.decode(URLDecoder.decode(ss[1], "utf-8"), "utf-8");
				if (q==0) {
					Matcher mat = pat_video_code.matcher(ss[1]);
					if (mat.find()) {
						video_code = mat.group(1);
					} else {
						video_code = "00000000";
						//t_list.clear();
						//it.remove();
						//return;
					}
					mat = pat_client.matcher(ss[1]);
					if (mat.find()) {
						client = mat.group(1);
					} else {
						System.out.println("no client:\t" + line);
					}
				}
				String ss0[] = ss[0].split(" ");
				String ss1[] = ss[1].split(" ");
				if (ss1[1].contains("?")) {
					ss1[1] = ss1[1].substring(0, ss1[1].indexOf("?"));
				}
				if (!(ss1[1].endsWith("ts") || ss1[1].endsWith("m3u8"))) {
					System.out.println("-------" + line);
					continue;
				}

				Matcher mat = pat_disk.matcher(ss1[1]);
				if (mat.find()) {
					diskid = mat.group(1);
				} else {
					diskid = "-1";
					System.out.println("Disk error!!!");
				}

				String ss11[] = ss1[1].split("/");//	/11/345/281/000/1/S4/1.ts
				String ss2[] = ss[2].split(" ");  //	 206 625147
				String t_bitrate = ss11[ss11.length-2];
				t_time += Double.parseDouble(ss0[0]);
				t_size += Integer.parseInt(ss2[2]);

				long timestamp = sdf.parse(ss0[4] + " " + ss0[5]).getTime()/1000;
				int bitrate = 0;
				if (ss1[1].contains("m3u8")) {
					m3u8_num++;
					if (t_bitrate.contains("S")) {
						Matcher mat_bitrate = pat_bitrate.matcher(line);
						if (mat_bitrate.find()) {
							if (mat_bitrate.group(1)==null) {
								bitrate = Integer.parseInt(mat_bitrate.group(2))/1000;
								s_bitrate.put(video_path + "\t" + t_bitrate, bitrate);
							} else {
								bitrate = Integer.parseInt(mat_bitrate.group(1))/1000;
								s_bitrate.put(video_path + "\t" + t_bitrate, bitrate);
							}
						} else {
							bitrate = st_bitrate.get(t_bitrate);
							t_bitrate = String.valueOf(bitrate);
							System.out.println("++++++++++++++++ " + line);
						}
					} else {
						try {
							bitrate = Integer.parseInt(t_bitrate);
						} catch (Exception e) {
							System.out.println(t_bitrate);
							System.out.println("================ " + line);
						}
					}
				} else {
					chunks.add(ss11[ss11.length-1]);
					try {
						if (t_bitrate.contains("S")) {
							if (s_bitrate.containsKey(video_path + "\t" + t_bitrate)) {
								bitrate = s_bitrate.get(video_path + "\t" + t_bitrate);
							} else {
								bitrate = st_bitrate.get(t_bitrate);
							}
						} else {
							bitrate = Integer.parseInt(t_bitrate);
						}
						if (bitrate<300) {
							if (bitrate>0)
								s1++;
						} else if (bitrate<500 ) {
							s2++;
						} else if (bitrate<800 ) {
							s3++;
						} else if (bitrate<=1300) {
							s4++;
						} else if (bitrate<=2300){
							s5++;
						} else if (bitrate<=4000) {
							s6++;
						} else {
							s7++;
						}
						if (!t_bitrate.equals(Bitrate)) {
							switch_num++;
							Bitrate = t_bitrate;
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println(t_bitrate);
						System.out.println("================ " + line);
					}
				}
				String out_bitrate = "";
				if (bitrate==0) {
					out_bitrate = t_bitrate;
				} else {
					out_bitrate = String.valueOf(bitrate);
				}

				//userid key(ip videoid device) video_code diskid time timestamp Bitrate filename ret size
				rf_format.writeBytes(userid + "\t" + entry.getKey() + "\t" + video_code + "\t" + diskid + '\t' + ss0[0] + "\t" + timestamp + "\t" + out_bitrate + "\t" + ss11[ss11.length-1] + "\t" + ss2[1] + "\t" + ss2[2] + "\t" + client + "\n");
				rf.writeBytes(line + "\n");
			}
			long endinfile = rf.getFilePointer();
			long endinfile_f = rf_format.getFilePointer();
			s_num = lognum - m3u8_num;
			speed = t_size/t_time;

			//server userid key(ip videoid device) video_code starttime endtime startline endline startinfile endinfile startinfile_f endinfor_f lognum s_num s1 s2 s3 s4 s5 m3u8_num t_time t_size speed switch_num
			out.write(cdn_server + "\t" + userid + "\t" + entry.getKey() + "\t" + video_code + "\t" + sdf2.format(new Date(time_s)) + "\t" + sdf2.format(new Date(time_e))
					 + "\t" + startline + "\t" + endline + "\t" + startinfile + "\t" + endinfile + "\t" + startinfile_f + "\t" + endinfile_f + "\t" + lognum + "\t" + s_num
					 + "\t" + s1 + "\t" + s2 + "\t" + s3 + "\t" + s4 + "\t" + s5 + "\t" + s6 + "\t" + s7 + "\t" + m3u8_num + "\t" + t_time + "\t" + t_size + "\t" + speed + "\t" + switch_num + "\t" + client + "\t" + chunks.size() + "\n");
			out.flush();
			startline += lognum;
			//clear session
			t_list.clear();
			it.remove();
		}
	}
}
