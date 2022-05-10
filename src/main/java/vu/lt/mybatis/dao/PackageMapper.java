package vu.lt.mybatis.dao;

import java.util.List;


import org.mybatis.cdi.Mapper;
import vu.lt.mybatis.model.Package;

@Mapper
public interface PackageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.PACKAGE
     *
     * @mbg.generated Sun May 08 21:57:40 EEST 2022
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.PACKAGE
     *
     * @mbg.generated Sun May 08 21:57:40 EEST 2022
     */
    int insert(Package record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.PACKAGE
     *
     * @mbg.generated Sun May 08 21:57:40 EEST 2022
     */
    Package selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.PACKAGE
     *
     * @mbg.generated Sun May 08 21:57:40 EEST 2022
     */
    List<Package> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PUBLIC.PACKAGE
     *
     * @mbg.generated Sun May 08 21:57:40 EEST 2022
     */
    int updateByPrimaryKey(Package record);

    List<Package> selectAvailableDeliveries();
}