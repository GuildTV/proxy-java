package gtv.proxy;

public class ProxySettings {
	
	private static String[] locations = new String[] {
		"/Applications/Compressor.app/Contents/Resources/English.lproj/Formats/QuickTime/Apple ProRes 422 (Proxy).setting",
		"/Applications/Compressor.app/Contents/Resources/English.lproj/Formats/QuickTime/Apple ProRes 422.setting",
		"/Applications/Compressor.app/Contents/Resources/English.lproj/Formats/QuickTime/Apple ProRes 422 (HQ).setting",
		"/Applications/Compressor.app/Contents/Resources/English.lproj/Formats/QuickTime/Apple ProRes 422 (LT).setting",
		"/Applications/Compressor.app/Contents/Resources/English.lproj/Formats/QuickTime/QuickTime H.264.setting"
	};
	
	public static String[] getNames() {
		return new String[] { 
			"ProRes 422 (Proxy)",
			"ProRes 422",
			"ProRes 422 (HQ)",
			"ProRes 422 (LT)",
			"H.264"
		};
	}
	
	public static String getLocationFromName(String name) {
		int index = 0;
		for(String s : getNames()) {
			if(s.equals(name)) {
				return locations[index];
			}
			index++;
		}
		return null;
	}
	
}
