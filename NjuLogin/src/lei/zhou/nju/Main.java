package lei.zhou.nju;

public class Main {
	public static void main(String[] args) {
		Login login = new Login("mg1533091", "404notfound");
		try {
			login.login();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
