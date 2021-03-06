package com.intellij.ide.projectWizard;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.openapi.module.JavaModuleType;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dmitry Avdeev
 *         Date: 20.09.13
 */
public class BuilderBasedProjectType extends ProjectCategory {

  private final ModuleBuilder myBuilder;

  public BuilderBasedProjectType(ModuleBuilder builder) {
    myBuilder = builder;
  }

  @NotNull
  @Override
  public ModuleBuilder createModuleBuilder() {
    return myBuilder;
  }

  /**
   * @author Dmitry Avdeev
   *         Date: 04.09.13
   */
  public static class Java extends BuilderBasedProjectType {

    public Java() {
      super(JavaModuleType.getModuleType().createModuleBuilder());
    }
  }
}
