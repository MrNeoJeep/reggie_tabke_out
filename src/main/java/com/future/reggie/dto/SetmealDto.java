package com.future.reggie.dto;


import com.future.reggie.pojo.Setmeal;
import com.future.reggie.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
