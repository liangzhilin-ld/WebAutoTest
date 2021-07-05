package com.hteis.webtest.entities;

public class DeviceData {

	public DeviceData() {
	}

	public DeviceData(String name, String enName, String bayName, String linkName, MgDeviceType type, String contact,
			String phone) {
		this.Name = name;
		this.EnName = enName;
		this.BayName = bayName;
		this.LinkName = linkName;
		this.Type = type;
		this.Contact = contact;
		this.Phone = phone;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DeviceData)) {
			return false;
		} else {
			DeviceData dev = (DeviceData)obj;
			return dev.Name.equals(this.Name)
				&& dev.EnName.equals(this.EnName)
				&& dev.BayName.equals(this.BayName)
				&& dev.LinkName.equals(this.LinkName)
				&& dev.Type == this.Type
				&& dev.Contact.equals(this.Contact)
				&& dev.Phone.equals(this.Phone);
		}
	}

	/*** 设备编号 */
	public String No;

	/*** 设备名称 */
	public String Name;

	/*** 设备英文名 */
	public String EnName;

	/*** 间隔 */
	public String BayName;

	/*** 链路 */
	public String LinkName;

	/*** 设备类型 */
	public MgDeviceType Type;

	/*** 现场联系人 */
	public String Contact;

	/*** 联系电话 */
	public String Phone;
}
