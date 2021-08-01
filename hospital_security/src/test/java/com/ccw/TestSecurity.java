package com.ccw;

import com.ccw.mapper.SysMenuMapper;
import org.junit.jupiter.api.Test;
import com.ccw.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestSecurity {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Test
    public void method(){
        List<String> result = sysRoleMapper.selectRoleCodesByUserId(3);
        System.out.println(result);
        System.out.println(sysMenuMapper.selectMenuPermsByUserId(3));
    }

}
