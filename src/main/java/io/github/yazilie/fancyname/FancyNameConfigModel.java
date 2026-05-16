package io.github.yazilie.fancyname;

import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

import static io.github.yazilie.fancyname.FancyName.MOD_ID;

@Modmenu(modId = MOD_ID)
@Config(name = MOD_ID, wrapperName = "FancyNameConfig")
public class FancyNameConfigModel {
    public boolean enabled = true;
    public boolean onlyStyles = false;
    public boolean playerList = true;
    public boolean nameTags = true;
}
