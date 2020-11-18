package th.co.geniustree.typescript;

import java.util.List;

public enum LicensingForm1FileType {

    A(FormType.LicensingForm1, "รายการเครื่องจักร เครื่องมือ พร้อมทั้งอุปกรณ์ที่ใช้ในการผลิต ชนิด ขนาด"),
    B(FormType.LicensingForm1, "เอกสารแสดงกระบวนการผลิตอาหารสัตว์ควบคุมเฉพาะ"),
    C(FormType.LicensingForm1, "แผนที่แสดงสถานที่ผลิตอาหารสัตว์ควบคุมเฉพาะ"),
    D(FormType.LicensingForm1, "แผนที่แสดงสถานที่เก็บอาหารสัตว์ควบคุมเฉพาะ"),
    E(FormType.LicensingForm1, "แผนที่แสดงสถานที่เก็บอาหารสัตว์ควบคุมเฉพาะ"),
    F(FormType.LicensingForm1, "สำเนาบัตรประชาชน หรือหนังสือเดินทางของผู้ลงทะเบียน"),
    G(FormType.LicensingForm1, "สำเนาหนังสือมอบอำนาจพร้อมหลักฐานการมอบอำนาจ (กรณีไม่ได้มาติดต่อด้วยตนเอง)"),
    H(FormType.LicensingForm1, "สำเนาหนังสือจดทะเบียนบริษัท"),
    I(FormType.LicensingForm1, "สำเนาหนังสือแต่งตั้งผู้ดำเนินกิจการ"),
    J(FormType.LicensingForm1, "ใบรับรองอิเล็กทรอนิกส์ (Digital Certificate)");

    private FormType formType;
    private String name;

    LicensingForm1FileType(FormType formType, String name) {
        this.formType = formType;
        this.name = name;
    }

    public FormType getFormType() {
        return formType;
    }

    public void setFormType(FormType formType) {
        this.formType = formType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
