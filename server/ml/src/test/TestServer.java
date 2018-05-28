package test;


/**
 * Command line interface to the Wire Java generator.
 *
 * <h3>Usage:</h3>
 *
 * <pre>
 * java WireCompiler --proto_path=&lt;path&gt; --java_out=&lt;path&gt;
 *     [--files=&lt;protos.include&gt;]
 *     [--includes=&lt;message_name&gt;[,&lt;message_name&gt;...]]
 *     [--excludes=&lt;message_name&gt;[,&lt;message_name&gt;...]]
 *     [--quiet]
 *     [--dry_run]
 *     [--android]
 *     [--compact]
 *     [file [file...]]
 * </pre>
 *
 * <p>If the {@code --includes} flag is present, its argument must be a comma-separated list
 * of fully-qualified message or enum names. The output will be limited to those messages
 * and enums that are (transitive) dependencies of the listed names. The {@code --excludes} flag
 * excludes types, and takes precedence over {@code --includes}.
 *
 * <p>If the {@code --registry_class} flag is present, its argument must be a Java class name. A
 * class with the given name will be generated, containing a constant list of all extension
 * classes generated during the compile. This list is suitable for passing to Wire's constructor
 * at runtime for constructing its internal extension registry.
 *
 * <p>If {@code --quiet} is specified, diagnostic messages to stdout are suppressed.
 *
 * <p>The {@code --dry_run} flag causes the compile to just emit the names of the source files that
 * would be generated to stdout.
 *
 * <p>The {@code --android} flag will cause all messages to implement the {@code Parcelable}
 * interface.
 *
 * <p>The {@code --compact} flag will emit code that uses reflection for reading, writing, and
 * toString methods which are normally implemented with code generation.
 */
public class TestServer {

	public static void main(String args[])
	{
		System.out.println("helloworld");
	}
	
	// ½Ó¿ÚÀà
	public interface Bean
	{
		String getClassName();
		
	}
}
