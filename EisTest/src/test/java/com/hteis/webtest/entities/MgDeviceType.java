package com.hteis.webtest.entities;

public enum MgDeviceType {

	PVPanel("PV-panel", "光伏组件", 0), 
	PVArray("PV-array", "光伏阵列", 1), 
	PVInverter("PV-inverter", "光伏逆变器", 2), 
	WindFarm("wind-farm", "风电场", 3), 
	WindTurbine("wind-turbine", "风机", 4), 
	WindInverter("wind-inverter", "风机逆变器", 5), 
	Battery("battery", "蓄电池", 6), 
	PCS("pcs", "储能逆变器", 7), 
	ChargePil("charge-pil", "充电桩", 8), 
	UPS("UPS", "不间断电源", 9);

	private String id;
	private String name;
	private int index;

	private MgDeviceType(String id, String name, int index) {
		this.id = id;
		this.name = name;
		this.index = index;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public int getIndex() {
		return this.index;
	}

	public static MgDeviceType getTypeFromId(String id) {
		if (id != null) {
			for(MgDeviceType type : MgDeviceType.values()){
				if(type.getId().equals(id)){
					return type;
				}
			}
		}
		return null;
	}
}
