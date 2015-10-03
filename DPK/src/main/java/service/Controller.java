package service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Path("/adapter")
public class Controller {
	private Document DPK;
	
	private final String pattern = "^\\s*(.*)\n.+Studi\\s+:\\s+(.*$)\n.+:\\s(\\d)\\/(\\d+)\n+.+:\\s+(\\w+)\\s\\/\\s(.*),\\s(\\d).+\n.+:\\s(\\d+)\\s\\/\\s(.*)(.*[\n])*.+=\\s*(\\d+)";
	private final String baseURL = "https://six.akademik.itb.ac.id/publik/";
	
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(
			@QueryParam("ps") String ps,
			@QueryParam("kode") String kode,
			@QueryParam("kelas") String kelas) {
		
		if (ps == null || kode == null || kelas == null){
			return Response.status(400)
					.entity(new Error("Request tidak sesuai format"))
					.build();
		}
		
		String url = baseURL + "daftarkelas.php?ps="
				+ ps + "&semester=1&tahun=2015&th_kur=2013";  
		Result result = new Result();
		try {
			if (!findURL(url, kode, kelas)) {
				return Response.status(Response.Status.NOT_FOUND)
						.entity(new Error("Tidak ditemukan kelas dengan kode " +  kode))
						.build();
			}
			result = parseDPK();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError()
					.entity(new Error("Terjadi kesalahan pada server")).build();
		}
		return Response.ok(result).build();

	}
	
	private boolean findURL(String url, String kode, String kelas)
			throws Exception {
		
		Document doc = Jsoup.connect(url).get();
        Elements matkuls = doc.select("ol li");
        for (Element matkul : matkuls) {
        	if (matkul.text().startsWith(kode)) {
        		Elements links = matkul.select("ul li a");
        		for (Element link : links) {
        			if (link.text().equals(kelas)) {
        				String urlDPK = baseURL + link.attr("href");
        				DPK = Jsoup.connect(urlDPK).get();
        				return true;
        			}
        		}
        		break;
        	}
        }
        return false;
	}
	
	private Result parseDPK() throws Exception {
		Result result = new Result();
		String page = DPK.select("pre").first().text().toString();
		Matcher m = Pattern.compile(pattern, Pattern.MULTILINE).matcher(page);
		if (m.find()) {
			result.setFakultas(m.group(1));
			result.setProdi(m.group(2));
			result.setSemester(Integer.parseInt((m.group(3))));
			result.setTahun(2000 + Integer.parseInt(m.group(4)));
			result.setKode(m.group(5));
			result.setMata_kuliah(m.group(6));
			result.setSks(Integer.parseInt(m.group(7)));
			result.setKelas(m.group(8));
			result.setDosen(m.group(9));
			result.setJumlah_peserta(Integer.parseInt(m.group(11)));
			String[] lines = page.split("\n");
			int i = 0;
			for (String line : lines) {
				Matcher match = Pattern.compile("(\\d{3})\\s+(\\d{8})\\s+(.*)").matcher(line);
				if ((match.find())) {
					result.addPeserta(i, match.group(2), match.group(3));
					i++;
				}
			}
		}
		return result;
	}
}
