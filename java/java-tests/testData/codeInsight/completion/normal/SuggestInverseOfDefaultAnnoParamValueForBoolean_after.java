@Anno(smth = true<caret>)
class C {
}

@interface Anno {
  boolean value() default true;
  boolean smth() default false;
}