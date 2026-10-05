# GwtBootstrap5 Demo

Source of the live demo at **<https://gwtbootstrap5.github.io/>**: every widget of [GwtBootstrap5](https://github.com/gwtbootstrap5/gwtbootstrap5) and [GwtBootstrap5 Extras](https://github.com/gwtbootstrap5/gwtbootstrap5-extras) running, next to the UiBinder code that produces it.

It is based on the [GwtBootstrap3 demo](https://github.com/gwtbootstrap3/gwtbootstrap3-demo) (Apache License 2.0), rewritten for Bootstrap 5.3 without GWTP or GIN.

## Building

The demo builds against the `master` branch of GwtBootstrap5 and its extras, as the `gwtbootstrap5-demo` module of [gwtbootstrap5-parent](https://github.com/gwtbootstrap5/gwtbootstrap5-parent):

```sh
git clone --recursive https://github.com/gwtbootstrap5/gwtbootstrap5-parent.git
cd gwtbootstrap5-parent
mvn install -Ddependency-check.skip=true
```

The compiled site is in `gwtbootstrap5-demo/target/gwtbootstrap5-demo-<version>/`; serve that folder with any static web server. Every push to `master` of this repository builds the demo and publishes it to GitHub Pages.

## Adding an example

Each example lives in its own `.ui.xml` file next to its page. The page creates it with a `UiBinder` and shows that same file as the example's code, so the code shown is always the code that runs. See `client/ui/Example.java`.

## License

Apache License 2.0, like GwtBootstrap5.
